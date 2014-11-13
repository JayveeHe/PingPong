package UI;

import java.awt.EventQueue;
import java.awt.Graphics;

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

import javax.swing.JTextPane;

import Items.ABasicItems;
import Items.Ball;
import Items.Board;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	InputStream in;
	OutputStream out;
	private JPanel CanvasPanel;

	// DrawThread dt = new DrawThread();

	private Board board_left;
	private Board board_right;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					frame.setVisible(true);

					class DrawThread extends Thread {
						JPanel CanvasPanel;

						public DrawThread(JPanel CanvasPanel) {
							this.CanvasPanel = CanvasPanel;
						}

						@Override
						public void run() {
							ItemManager.addDomain(new Board("left", 0, 50));
							ItemManager.addDomain(new Ball(CanvasPanel, "ball",
									0, 0, 10));
							ItemManager.addDomain(new Board("right",
									CanvasPanel.getWidth() - 10, CanvasPanel
											.getHeight() / 2));
							long time = System.currentTimeMillis();
							int count = 0;
							while (true) {
								// System.out.println("开始时间："+System.currentTimeMillis());
								if (count == 0) {
									time = System.currentTimeMillis();
								}
								if (null != ItemManager.itemMap) {
									for (ABasicItems items : ItemManager.itemMap
											.values()) {
										items.onDraw(time,
												CanvasPanel.getGraphics());
//										time = System.currentTimeMillis();
									}
								}
								count++;
								if (System.currentTimeMillis() - time > 0) {
									System.out.println(count);
									count = 0;
								}
//								System.out.println("结束时间："
//										+ System.currentTimeMillis());
								 try {
								 Thread.sleep(1);
								 } catch (InterruptedException e) {
								 // TODO Auto-generated catch block
								 e.printStackTrace();
								 }
							}
						}
					}
					DrawThread dt = new DrawThread(frame.getCanvasPanel());
					dt.start();
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

		CanvasPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				System.out.println(arg0.getKeyCode());
				CanvasPanel.repaint();
				Board left = (Board) ItemManager.itemMap.get("left");
				CanvasPanel.repaint();
				switch (arg0.getKeyCode()) {
				case 38:// 上
					System.out.println(left.getX() + "-" + left.getY());
					left.setLocation(left.getX(), left.getY() - 5);
					break;
				case 40:// 下
					left.setLocation(left.getX(), left.getY() + 5);
					break;
				default:
					break;
				}
			}
		});
		CanvasPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				CanvasPanel.requestFocus();
				System.out.println(arg0.getX() + "=" + arg0.getY());
			}
		});
		CanvasPanel.setBounds(5, 39, 477, 216);
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
					out.write(text.getBytes("utf-8"));
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
				// connect(iP);
				ConnectThread ct = new ConnectThread(iP);
				ct.start();
			}
		});
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isHost = checkBox.isSelected();
			}
		});

	}

	public JPanel getCanvasPanel() { // 啥意思
		return CanvasPanel;
	}

	public class ReceiverThread extends Thread {
		InputStream in;
		Socket socket;

		public ReceiverThread(Socket socket) {
			this.socket = socket;
			try {
				this.in = socket.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
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
	}

	public class SenderThread extends Thread {
		Socket socket;
		OutputStream out;

		public SenderThread(Socket socket, OutputStream out) {
			this.out = out;
			this.socket = socket;
		}

		@Override
		public void run() {
			while (!socket.isClosed()) {
				// if(out.)
			}
		}
	}

	public class ConnectThread extends Thread {
		String IP;

		public ConnectThread(String IP) {
			this.IP = IP;
		}

		@Override
		public void run() {
			try {
				if (isHost) {
					ServerSocket serverSocket = new ServerSocket(1234);
					socket = serverSocket.accept();
					serverSocket.close();
					isConnected = true;
					showText.setText("已连接");
					System.out.println(socket.getRemoteSocketAddress());
					System.out.println("连接成功");

				} else {
					socket = new Socket(IP, 1234);
					isConnected = true;
					showText.setText("已连接");
				}
				// in = socket.getInputStream();
				out = socket.getOutputStream();
				new ReceiverThread(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
