package uk.org.downesward.traveller.encounters;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JSpinner;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import uk.org.downesward.traveller.common.UPP;
import uk.org.downesward.traveller.encounters.TableGenerator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.event.ActionListener;

public class Configure {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final Action action = new SwingAction();
	private JSpinner spnSize;
	private JSpinner spnAtmosphere;
	private JSpinner spnHydrographics;
	private SpinnerNumberModel sizeModel = new SpinnerNumberModel(7, 0, 10, 1);
	private SpinnerNumberModel atmosphereModel = new SpinnerNumberModel(7, 0, 10, 1);
	private SpinnerNumberModel hydrographicsModel = new SpinnerNumberModel(7, 0, 10, 1);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Configure window = new Configure();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Configure() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblSize = new JLabel("Size");
		GridBagConstraints gbc_lblSize = new GridBagConstraints();
		gbc_lblSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblSize.gridx = 0;
		gbc_lblSize.gridy = 0;
		frame.getContentPane().add(lblSize, gbc_lblSize);
		
		spnSize = new JSpinner();
		GridBagConstraints gbc_spnSize = new GridBagConstraints();
		gbc_spnSize.insets = new Insets(0, 0, 5, 0);
		gbc_spnSize.gridx = 1;
		gbc_spnSize.gridy = 0;
		spnSize.setModel(sizeModel);
		frame.getContentPane().add(spnSize, gbc_spnSize);
		
		JLabel lblAtmosphere = new JLabel("Atmosphere");
		GridBagConstraints gbc_lblAtmosphere = new GridBagConstraints();
		gbc_lblAtmosphere.insets = new Insets(0, 0, 5, 5);
		gbc_lblAtmosphere.gridx = 0;
		gbc_lblAtmosphere.gridy = 1;
		frame.getContentPane().add(lblAtmosphere, gbc_lblAtmosphere);
		
		spnAtmosphere = new JSpinner();
		GridBagConstraints gbc_spnAtmosphere = new GridBagConstraints();
		gbc_spnAtmosphere.insets = new Insets(0, 0, 5, 0);
		gbc_spnAtmosphere.gridx = 1;
		gbc_spnAtmosphere.gridy = 1;
		spnAtmosphere.setModel(atmosphereModel);
		frame.getContentPane().add(spnAtmosphere, gbc_spnAtmosphere);
		
		JLabel lblHydrographics = new JLabel("Hydrographics");
		GridBagConstraints gbc_lblHydrographics = new GridBagConstraints();
		gbc_lblHydrographics.insets = new Insets(0, 0, 5, 5);
		gbc_lblHydrographics.gridx = 0;
		gbc_lblHydrographics.gridy = 2;
		frame.getContentPane().add(lblHydrographics, gbc_lblHydrographics);
		
		spnHydrographics = new JSpinner();
		GridBagConstraints gbc_spnHydrographics = new GridBagConstraints();
		gbc_spnHydrographics.insets = new Insets(0, 0, 5, 0);
		gbc_spnHydrographics.gridx = 1;
		gbc_spnHydrographics.gridy = 2;
		spnHydrographics.setModel(hydrographicsModel);
		frame.getContentPane().add(spnHydrographics, gbc_spnHydrographics);
		
		JRadioButton rdbtnd = new JRadioButton("1D6");
		buttonGroup.add(rdbtnd);
		GridBagConstraints gbc_rdbtnd = new GridBagConstraints();
		gbc_rdbtnd.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnd.gridx = 0;
		gbc_rdbtnd.gridy = 3;
		frame.getContentPane().add(rdbtnd, gbc_rdbtnd);
		
		final JRadioButton rdbtnd_1 = new JRadioButton("2D6");
		buttonGroup.add(rdbtnd_1);
		GridBagConstraints gbc_rdbtnd_1 = new GridBagConstraints();
		gbc_rdbtnd_1.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnd_1.gridx = 1;
		gbc_rdbtnd_1.gridy = 3;
		rdbtnd_1.setSelected(true);
		frame.getContentPane().add(rdbtnd_1, gbc_rdbtnd_1);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UPP upp = new UPP();
				upp.atmosphere.setValue(atmosphereModel.getNumber().intValue());
				upp.hydro.setValue(hydrographicsModel.getNumber().intValue());
				upp.size.setValue(sizeModel.getNumber().intValue());
				
				TableGenerator tg = new TableGenerator();
				if (rdbtnd_1.isSelected()) {

					tg.Generate(2, upp);
				}
				else {
					tg.Generate(1, upp);
				}
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				try {
					DocumentBuilder parser = factory.newDocumentBuilder();
					Document doc = parser.newDocument();
					Element root = doc.createElement("encounters");
					tg.WriteToXML(root);
					doc.appendChild(root);
				}
				catch (ParserConfigurationException e)
				{
				
				}
				
			}
		});
		btnGenerate.setAction(action);
		GridBagConstraints gbc_btnGenerate = new GridBagConstraints();
		gbc_btnGenerate.insets = new Insets(0, 0, 0, 5);
		gbc_btnGenerate.gridx = 0;
		gbc_btnGenerate.gridy = 4;
		frame.getContentPane().add(btnGenerate, gbc_btnGenerate);
	}

	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "Generate");
			putValue(SHORT_DESCRIPTION, "Generate encounter table");
		}
		public void actionPerformed(ActionEvent e) {
			UPP upp = new UPP();
			upp.size.setValue(sizeModel.getNumber().intValue());
			upp.atmosphere.setValue(atmosphereModel.getNumber().intValue());
			upp.hydro.setValue(hydrographicsModel.getNumber().intValue());
		}
	}
	protected JSpinner getSpnSize() {
		return spnSize;
	}
	protected JSpinner getSpnAtmosphere() {
		return spnAtmosphere;
	}
	protected JSpinner getSpnHydrographics() {
		return spnHydrographics;
	}
}
