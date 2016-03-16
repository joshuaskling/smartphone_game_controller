namespace Bluetooth_v0._5
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.tbOutput = new System.Windows.Forms.TextBox();
            this.tbInput = new System.Windows.Forms.TextBox();
            this.listBox1 = new System.Windows.Forms.ListBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.rbClient = new System.Windows.Forms.RadioButton();
            this.rbServer = new System.Windows.Forms.RadioButton();
            this.bgo = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tbOutput
            // 
            this.tbOutput.Location = new System.Drawing.Point(34, 47);
            this.tbOutput.Multiline = true;
            this.tbOutput.Name = "tbOutput";
            this.tbOutput.Size = new System.Drawing.Size(556, 391);
            this.tbOutput.TabIndex = 0;
            // 
            // tbInput
            // 
            this.tbInput.Location = new System.Drawing.Point(34, 457);
            this.tbInput.Name = "tbInput";
            this.tbInput.Size = new System.Drawing.Size(556, 20);
            this.tbInput.TabIndex = 1;
            this.tbInput.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.tbInput_KeyPress);
            // 
            // listBox1
            // 
            this.listBox1.FormattingEnabled = true;
            this.listBox1.Location = new System.Drawing.Point(626, 182);
            this.listBox1.Name = "listBox1";
            this.listBox1.Size = new System.Drawing.Size(200, 56);
            this.listBox1.TabIndex = 2;
            this.listBox1.SelectedIndexChanged += new System.EventHandler(this.listBox1_SelectedIndexChanged);
            this.listBox1.DoubleClick += new System.EventHandler(this.listBox1_DoubleClick);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.rbServer);
            this.groupBox1.Controls.Add(this.rbClient);
            this.groupBox1.Location = new System.Drawing.Point(626, 47);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(200, 100);
            this.groupBox1.TabIndex = 3;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Connection Type";
            // 
            // rbClient
            // 
            this.rbClient.AutoSize = true;
            this.rbClient.Location = new System.Drawing.Point(6, 33);
            this.rbClient.Name = "rbClient";
            this.rbClient.Size = new System.Drawing.Size(51, 17);
            this.rbClient.TabIndex = 0;
            this.rbClient.TabStop = true;
            this.rbClient.Text = "Client";
            this.rbClient.UseVisualStyleBackColor = true;
            // 
            // rbServer
            // 
            this.rbServer.AutoSize = true;
            this.rbServer.Checked = true;
            this.rbServer.Location = new System.Drawing.Point(109, 33);
            this.rbServer.Name = "rbServer";
            this.rbServer.Size = new System.Drawing.Size(56, 17);
            this.rbServer.TabIndex = 1;
            this.rbServer.TabStop = true;
            this.rbServer.Text = "Server";
            this.rbServer.UseVisualStyleBackColor = true;
            // 
            // bgo
            // 
            this.bgo.Location = new System.Drawing.Point(626, 153);
            this.bgo.Name = "bgo";
            this.bgo.Size = new System.Drawing.Size(75, 23);
            this.bgo.TabIndex = 4;
            this.bgo.Text = "Connect";
            this.bgo.UseVisualStyleBackColor = true;
            this.bgo.Click += new System.EventHandler(this.bgo_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1120, 492);
            this.Controls.Add(this.bgo);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.listBox1);
            this.Controls.Add(this.tbInput);
            this.Controls.Add(this.tbOutput);
            this.Name = "Form1";
            this.Text = "Form1";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox tbOutput;
        private System.Windows.Forms.TextBox tbInput;
        private System.Windows.Forms.ListBox listBox1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RadioButton rbServer;
        private System.Windows.Forms.RadioButton rbClient;
        private System.Windows.Forms.Button bgo;
    }
}

