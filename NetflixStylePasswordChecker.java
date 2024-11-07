import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class NetflixStylePasswordChecker extends JFrame implements ActionListener {

    private JTextField passwordField;
    private JLabel resultLabel;

    public NetflixStylePasswordChecker() {
        // Set up the frame with dark theme
        setTitle("Password Strength Checker");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 20)); // Dark background

        // Header label
        JLabel headerLabel = new JLabel("Netflix-Inspired Password Checker", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Center panel for input and button
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));
        centerPanel.setBackground(new Color(20, 20, 20));

        // Password input field
        passwordField = new JTextField();
        passwordField.setBackground(new Color(30, 30, 30));
        passwordField.setForeground(Color.WHITE);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        centerPanel.add(passwordField);

        // Check button
        JButton checkButton = new JButton("Check Strength");
        checkButton.setBackground(new Color(229, 9, 20)); // Netflix red
        checkButton.setForeground(Color.WHITE);
        checkButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkButton.setFocusPainted(false);
        checkButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        checkButton.addActionListener(this);
        centerPanel.add(checkButton);

        // Result label
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setForeground(Color.WHITE);
        centerPanel.add(resultLabel);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static String checkPasswordStrength(String password) {
        int length = password.length();
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecialChar = true;
            }
        }

        // Check password strength based on criteria
        if (length >= 8 && hasUppercase && hasLowercase && hasDigit && hasSpecialChar) {
            return "Strong";
        } else if (length >= 6 && ((hasUppercase && hasLowercase) || (hasLowercase && hasDigit) || (hasUppercase && hasDigit))) {
            return "Moderate";
        } else {
            return "Weak";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String password = passwordField.getText();
        String strength = checkPasswordStrength(password);
        resultLabel.setText("Password Strength: " + strength);

        // Change color based on strength
        switch (strength) {
            case "Strong":
                resultLabel.setForeground(Color.GREEN);
                break;
            case "Moderate":
                resultLabel.setForeground(Color.ORANGE);
                break;
            default:
                resultLabel.setForeground(Color.RED);
                break;
        }

        // Save password and strength to CSV file
        saveToCSV(password, strength);
    }

    private void saveToCSV(String password, String strength) {
        try (FileWriter csvWriter = new FileWriter("passwords.csv", true)) {
            csvWriter.append(password)
                     .append(",")
                     .append(strength)
                     .append("\n");
            csvWriter.flush();
            System.out.println("Password saved to CSV file.");
        } catch (IOException ex) {
            System.err.println("Error writing to CSV file: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NetflixStylePasswordChecker());
    }
}
