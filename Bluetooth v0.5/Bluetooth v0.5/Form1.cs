using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using InTheHand;
using InTheHand.Net.Bluetooth;
using InTheHand.Net.Ports;
using InTheHand.Net.Sockets;
using System.IO;
namespace Bluetooth_v0._5
{
    public partial class Form1 : Form
    {
        List<string> items;
        public Form1()
        {
            items = new List<string>();
            InitializeComponent();
        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void bgo_Click(object sender, EventArgs e)
        {
            if (serverStarted)
            {
                updateUI("server already started");
                return;
            }
            else
            {
                if (rbClient.Checked)
                {
                    startScan();
                }
                else
                {
                    connectAsServer();
                }
            }
        }
        private void startScan()
        {
            listBox1.DataSource = null;
            listBox1.Items.Clear();
            items.Clear();
            Thread BluetoothServerThread = new Thread(new ThreadStart(Scanner));
            BluetoothServerThread.Start();

        }
        BluetoothDeviceInfo[] devices;
        private void Scanner()
        {
          
            updateUI("Starting Scan...");
            BluetoothClient client = new BluetoothClient();
            devices = client.DiscoverDevicesInRange();
            updateUI("Scan complete");
            updateUI(devices.Length.ToString() + " devices discovered");
            foreach(BluetoothDeviceInfo d in devices)
            {
                items.Add(d.DeviceName);
            }
            updateDeviceList();
        }
        private void connectAsServer()
        {
            Thread BluetoothServerThread = new Thread(new ThreadStart(ServerConnectThread));
            BluetoothServerThread.Start();
        }

        private void connectAsClient()
        {
            throw new NotImplementedException();
        }
        Guid mUUID = new Guid("00001101-0000-1000-8000-00805F9B34FB");
        bool serverStarted = false;
        public void ServerConnectThread()
        {
            serverStarted = true;
            updateUI("Server started, waiting for clients");
            BluetoothListener blueListener = new BluetoothListener(mUUID);
            blueListener.Start();
            BluetoothClient conn = blueListener.AcceptBluetoothClient();
            updateUI("Client has connected");
            Stream mStream = conn.GetStream();
            while(true)
            {
                try
                {
                    byte[] received = new byte[1024];
                    mStream.Read(received, 0, received.Length);
                    updateUI("Received" + Encoding.ASCII.GetString(received));
                    byte[] sent = Encoding.ASCII.GetBytes("Hello World");
                    mStream.Write(sent, 0, sent.Length);
                }
                catch (IOException exception)
                {
                    updateUI("Client has disconnected");
                    break;
                }

            }

        }

        private void updateUI(string message)
        {
            Func<int> del = delegate()
            {
                tbOutput.AppendText(message + System.Environment.NewLine);
                return 0;
            };
            Invoke(del);
        }
        private void updateDeviceList()
        {
            Func<int> del = delegate()
            {
                listBox1.DataSource = items;
                return 0;
            };
            Invoke(del);
        }
        BluetoothDeviceInfo deviceInfo;
        private void listBox1_DoubleClick(object sender, EventArgs e)
        {
            deviceInfo = devices.ElementAt(listBox1.SelectedIndex);
            updateUI(deviceInfo.DeviceName + "was selected, attempting connection...");

            if(pairDevice())
            {
                updateUI("pair success");
                Thread bluethoothClientThread = new Thread(new ThreadStart(ClientconnectThread));
                bluethoothClientThread.Start();
            }
            else
            {
                updateUI("pair failed");
            }
        }
        private void ClientconnectThread()
        {
            BluetoothClient client = new BluetoothClient();
            updateUI("attempting connect");
            client.BeginConnect(deviceInfo.DeviceAddress, mUUID,this.bluetoothClientConnectCallback,client);
        }
        void bluetoothClientConnectCallback(IAsyncResult result)
        {
            BluetoothClient client = (BluetoothClient)result.AsyncState;
            client.EndConnect(result);

            Stream stream = client.GetStream();
            stream.ReadTimeout = 100;
            while(true)
            {
                while (!ready) ;
                stream.Write(message, 0, message.Length);
            }
        }
        string myPin = "1234";
        private bool pairDevice()
        {
            if(!deviceInfo.Authenticated)
            {
                if(!BluetoothSecurity.PairRequest(deviceInfo.DeviceAddress,myPin))
                {
                    return false;

                }
            }
            return true;

        }
        bool ready = false;
        byte[] message;
        private void tbInput_KeyPress(object sender, KeyPressEventArgs e)
        {
            if(e.KeyChar == 13)
            {
                message = Encoding.ASCII.GetBytes(tbInput.Text);
                ready = true;
                tbInput.Clear();
            }
        }
    }
}
