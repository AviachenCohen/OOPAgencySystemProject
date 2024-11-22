import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel title = new JLabel("Hi There! Welcome To My Agency System");
		title.setFont(new Font("Britannic Bold", Font.BOLD, 30));
		title.setForeground(Color.PINK);
		title.setBounds(103, 39, 581, 38);
		contentPane.add(title);

		JLabel lblNewLabel_1 = new JLabel("Operators Working Time:");
		lblNewLabel_1.setFont(new Font("Britannic Bold", Font.BOLD, 16));
		lblNewLabel_1.setBounds(146, 142, 197, 23);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Number of Executives:");
		lblNewLabel_2.setFont(new Font("Britannic Bold", Font.BOLD, 16));
		lblNewLabel_2.setBounds(146, 204, 179, 23);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Number of Operations for the Day:");
		lblNewLabel_3.setFont(new Font("Britannic Bold", Font.BOLD, 16));
		lblNewLabel_3.setBounds(146, 263, 268, 23);
		contentPane.add(lblNewLabel_3);

		JSpinner OperatorsWorkingTimeSpinner = new JSpinner();
		OperatorsWorkingTimeSpinner.setModel(new SpinnerNumberModel(1.0, null, null, 0.1));
		OperatorsWorkingTimeSpinner.setBackground(Color.WHITE);
		OperatorsWorkingTimeSpinner.setBounds(459, 142, 45, 23);
		contentPane.add(OperatorsWorkingTimeSpinner);

		JSpinner NumberOfExecutivesSpinner = new JSpinner();
		NumberOfExecutivesSpinner.setModel(new SpinnerNumberModel(0, null, null, 1));
		NumberOfExecutivesSpinner.setBounds(459, 204, 45, 24);
		contentPane.add(NumberOfExecutivesSpinner);

		JSpinner NumberOfOperationForDaySpinner = new JSpinner();
		NumberOfOperationForDaySpinner.setModel(new SpinnerNumberModel(10, null, null, 1));
		NumberOfOperationForDaySpinner.setBounds(459, 263, 45, 24);
		contentPane.add(NumberOfOperationForDaySpinner);

		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ee) {

				double operatorsWorkingTime = (double) OperatorsWorkingTimeSpinner.getValue();
				int numOfExecutives = (int) NumberOfExecutivesSpinner.getValue();
				int numOfOper = (int) NumberOfOperationForDaySpinner.getValue();
				if (numOfExecutives > 8) {
					JOptionPane.showMessageDialog(null, "Number of Executivies Can Not be More Than 8!!");
					NumberOfExecutivesSpinner.setValue(8);
					numOfExecutives = 8;
				}
				try {
					Agency agency = new Agency("Calls", operatorsWorkingTime, numOfExecutives, numOfOper);
					agency.runP();
					agency = null;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});

		startButton.setFont(new Font("Britannic Bold", Font.PLAIN, 12));
		startButton.setBounds(146, 328, 85, 21);
		contentPane.add(startButton);

		JButton exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setFont(new Font("Britannic Bold", Font.PLAIN, 12));
		exitButton.setBounds(419, 328, 85, 21);
		contentPane.add(exitButton);
	}

}
