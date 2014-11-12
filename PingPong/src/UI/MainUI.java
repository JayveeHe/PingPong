package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import java.awt.Color;

public class MainUI extends JFrame {

	private JPanel contentPane;
	private JTextField text_IP;
	private String iP;
	private JCheckBox checkBox;

	private Boolean isHost = false;
	private Boolean isConnected = false;
	private JTextField text_input;
	private JPanel panel_1;
	private JButton button;
	final JTextPane showText = new JTextPane();
	Socket socket;
	private JPanel CanvasPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		CanvasPanel = new JPanel();
		CanvasPanel.setBounds(5, 39, 477, 216);
		CanvasPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				System.out.println(arg0.getKeyChar());
			}
		});
		contentPane.setLayout(null);
		text_IP = new JTextField();
		text_IP.setBounds(5, 5, 222, 23);
		contentPane.add(text_IP);
		text_IP.setColumns(10);
		FlowLayout fl_CanvasPanel = (FlowLayout) CanvasPanel.getLayout();
		fl_CanvasPanel.setAlignment(FlowLayout.RIGHT);
		contentPane.add(CanvasPanel);

		panel_1 = new JPanel();
		panel_1.setBounds(5, 265, 424, 33);
		contentPane.add(panel_1);

		text_input = new JTextField();
		panel_1.add(text_input);
		text_input.setColumns(10);

		button = new JButton("发送");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = text_input.getText();
				try {
					socket.getOutputStream().write(text.getBytes("utf-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		panel_1.add(button);

		checkBox = new JCheckBox("是否主机");
		checkBox.setBounds(233, 5, 73, 23);
		contentPane.add(checkBox);

		JButton btn_connect = new JButton("连接");
		btn_connect.setBounds(313, 5, 57, 23);
		contentPane.add(btn_connect);

		showText.setBackground(Color.LIGHT_GRAY);
		showText.setEditable(false);
		showText.setText("未连接");
		showText.setBounds(380, 7, 49, 21);
		contentPane.add(showText);
		btn_connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				iP = text_IP.getText();
				connect(iP);
			}
		});
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isHost = checkBox.isSelected();
			}
		});
	}

	public void connect(String IP) {
		if (isHost) {
			try {
				ServerSocket serverSocket = new ServerSocket(1234);
				socket = serverSocket.accept();
				serverSocket.close();
				isConnected = true;
				showText.setText("已连接");
				System.out.println(socket.getRemoteSocketAddress());
				final InputStream in = socket.getInputStream();
				new Runnable() {
					public void run() {
						while (!socket.isClosed()) {
							int count = 0;
							while (count == 0) {
								try {
									count = in.available();
									byte[] b = new byte[count];
									in.read(b);
									String text = new String(b, "utf-8");
									if (text.length() != 0)
										System.out.println(text);
									if (text.equals("exit")) {
										socket.close();
										isConnected = false;
										showText.setText("未连接");
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}.run();
				System.out.println("连接成功");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				socket = new Socket(IP, 1234);
				isConnected = true;
				showText.setText("已连接");
				OutputStream outputStream = socket.getOutputStream();
				// System.out.println(outputStream.toString());
				// outputStream.write("nihao".getBytes("UTF-8"));
				// socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class myPanel extends JPanel {

		@Override
		public void paint(Graphics g) {
			// super.paint(arg0);
		}
	}
}