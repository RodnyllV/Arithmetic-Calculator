import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Element extends JFrame implements ActionListener {
    private JButton button;
    private String overhaulKey = null;
    JTextField textField;
    
    public void setCharacter(String newCharacter) {
        button.setText(newCharacter);
    }

    public void setToPanel(JPanel panel) {
        panel.add(button);
    }

    Element(String text, JTextField textField, String overhaulString) {
        button = new JButton();
        button.setSize(45, 45);
        button.setText(text);
        button.setFocusable(false);
        button.addActionListener(this);

        overhaulString = (overhaulString != null) ? overhaulString : "";
        overhaulKey = overhaulString;

        this.textField = textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            this.textField.setText(this.textField.getText() + ((overhaulKey == "") ? button.getText() : overhaulKey));
        }
    }
}