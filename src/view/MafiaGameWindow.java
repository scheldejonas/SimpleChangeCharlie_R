/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.MafiaGame;
import model.Player;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import model.Highscore;
import model.BaseCountry;
import model.BaseDrug;

/**
 *
 * @author CHRIS
 */
public class MafiaGameWindow extends javax.swing.JFrame {

    MafiaGame mainGame;
    
    /**
     * Creates new form TestGUI.
     * Gets the list of buying drugs and selling drugs for the first time game starts
     *
     * @param main
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MafiaGameWindow(MafiaGame main) {
        this.mainGame = main;
        initComponents();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        updateModelBoxes();
        jSliderBuyDrugs.setValue(0);
        jSliderSellDrugs.setValue(0);
        jSliderSellDrugs.setMaximum(0);
        jButtonRestartGame.setVisible(false);
        this.setVisible(true);
    }
    
    /**
     * Get's the correct substringed name of the current selected buying drug.
     * @return String as Drugname
     */
    public String getSelectedBuyingDrugName() {
        String selected = jComboBoxBuyingDrugs.getSelectedItem().toString();
        return selected.substring(0, selected.indexOf("-") - 1);
    }
    
    /**
     * Get's the buying Slider Drug Amount.
     * @return selectedAmount
     */
    public int getSelectedBuyingDrugAmount() {
        int selectedAmount = jSliderBuyDrugs.getValue();
        return selectedAmount;
    }
    
    /**
     * Get's the correct substringed name of the current selected buying drug. 
     * @return String as Drugname
     */
    public String getSelectedSellingDrugName() {
        Object object = jComboBoxSellingDrugs.getSelectedItem();
        if (object != null) {
            String selected = object.toString();
            return selected.substring(0, selected.indexOf("-") - 1);
        }
        return "null";
    }
    
    /**
     * Get's the selling Slider Drug Amount.
     * @return selectedAmount
     */
    public int getSelectedSellingDrugAmount() {
        int selectedAmount = jSliderSellDrugs.getValue();
        return selectedAmount;
    }
    
    /**
     * Updates all Model Boxes and Sliders on "Drugs" tab.
     */
    public void updateModelBoxes() {
        jComboBoxBuyingDrugs.setModel(getBuyDrugModel());
        jComboBoxSellingDrugs.setModel(getSellDrugModel());
        jLabelCurrentMoney.setText("Current money: $" + mainGame.getPlayer().getMoney());
        int amount = mainGame.getPlayerCurrentDrugAmount(getSelectedSellingDrugName());
        jSliderSellDrugs.setMaximum(amount);
        amount = mainGame.getCurrentCountry().getDrug(getSelectedBuyingDrugName()).getAmount();
        jSliderBuyDrugs.setMaximum(amount);
        int healSlider = 100 - mainGame.getPlayer().getHealth();
        int minorTicks = healSlider / 20;
        int majorTicks = healSlider / 10;
        jSliderBuyLife.setMaximum(healSlider);
        jSliderBuyLife.setMinorTickSpacing(minorTicks);
        jSliderBuyLife.setMajorTickSpacing(majorTicks);
        jSliderBuyLife.setValue(0);
        int lifeCost = mainGame.getCurrentCountry().getHealthPrice();
        jLabelLifeCost.setText("Life: $" + lifeCost + " pr. %");
        jLabelWelcomeText.setText("Welcome to " + mainGame.getCurrentCountry().getName() + "!");
        jComboBoxCountries.setModel(getTravelModel());
        jLabelCurrentTurn.setText("Current turn: " + mainGame.getTurn());
        int health = mainGame.getPlayer().getHealth();
        jProgressBarCurrentHP.setValue(health);
        jProgressBarCurrentHP.setString(health + "/100");
        if (health == 0 || mainGame.getTurn() >= 20) {
            endGame();
            try {
                mainGame.EndGame();
                DisplayEnd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        updateHighScores();
    }

    public void endGame() {
        for (BaseDrug d : mainGame.getPlayer().getDrugs()) {
            try {
                mainGame.sellDrug(d.getName(), d.getAmount());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void DisplayEnd() {
        JOptionPane.showMessageDialog(null, "Game Over!" + System.lineSeparator() +
                                            "You ended with $" + mainGame.getPlayer().getMoney());
        jTabbedPane1.setEnabledAt(0, false);
        jTabbedPane1.setEnabledAt(1, false);
        jTabbedPane1.setEnabledAt(2, false);
        mainGame.readHighscoresFromDatabase();
        jButtonRestartGame.setVisible(true);
        jTabbedPane1.setSelectedIndex(3);
    }
    
    public DefaultComboBoxModel getSellDrugModel() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.removeAllElements();
        Player player = mainGame.getPlayer();
        ArrayList<BaseDrug> playerDrugs = player.getDrugs();
        BaseCountry country = mainGame.getCurrentCountry();
        ArrayList<BaseDrug> countryDrugs = country.getDrugs();
        for (int i = 0; i < playerDrugs.size(); i++) {
            BaseDrug d = playerDrugs.get(i);
            for (BaseDrug cDrug : countryDrugs) {
                if (d.getName().equals(cDrug.getName())) {
                    if (d.getAmount() > 0) {
                        model.addElement(d.getName() + " - " + cDrug.getPrice());
                    }
                }
            }
        }
        return model;
    }
    
    
    public DefaultComboBoxModel getBuyDrugModel() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.removeAllElements();
        BaseCountry country = mainGame.getCurrentCountry();
        ArrayList<BaseDrug> drugs = country.getDrugs();
        for (int i = 0; i < drugs.size(); i++) {
            BaseDrug d = drugs.get(i);
            if (d.getAmount() > 0) {
                model.addElement(d.getName() + " - " + d.getPrice());
            }
        }
        return model;
    }
    
    public DefaultComboBoxModel getTravelModel() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.removeAllElements();
        BaseCountry country = mainGame.getCurrentCountry();
        ArrayList<BaseCountry> countries = mainGame.getCountries();
        for (int i = 0; i < countries.size(); i++) {
            BaseCountry c = countries.get(i);
            if (!c.getName().equals(country.getName())) {
                model.addElement(c.getName());
            }
        }
        return model;
    }
    
    /**
     * Updates the High Scores table with data inserted like normal european reading direction.
     * @see getHighScoresFromDatabase() in control.MafiaGame
     */
    public void updateHighScores() {
        DefaultTableModel tModel = new DefaultTableModel();
        ArrayList<Highscore> highscores = mainGame.getHighscores(); //Get highscores
        tModel.addColumn("Name");                       //Creates the username column in the modle
        tModel.addColumn("Scores");                         //Create the score column in the model
        tModel.setRowCount(highscores.size());      //Making the table long enugh to import varias data
        for (int i = 0; i < highscores.size(); i++) {
            Highscore highscore = highscores.get(i);
            tModel.setValueAt(highscore.getName(), i, 0);
            tModel.setValueAt(highscore.getPoints(), i, 1);
        }
        jTableHighScore.setModel(tModel); //Inserting the prereadied table to the GUI.
        jTableHighScore.setEnabled(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabelWelcomeText = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxBuyingDrugs = new javax.swing.JComboBox<>();
        jComboBoxSellingDrugs = new javax.swing.JComboBox<>();
        jLabelCurrentMoney = new javax.swing.JLabel();
        jButtonBuyDrugs = new javax.swing.JButton();
        jSliderBuyDrugs = new javax.swing.JSlider();
        jSliderSellDrugs = new javax.swing.JSlider();
        jButtonSellDrugs = new javax.swing.JButton();
        jLabelCurrentTurn = new javax.swing.JLabel();
        jProgressBarCurrentHP = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jButtonBuyLife = new javax.swing.JButton();
        jSliderBuyLife = new javax.swing.JSlider();
        jLabelLifeCost = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBoxCountries = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jButtonTravel = new javax.swing.JButton();
        jButtonHighFriendYakuza = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabelNiceClothesTerms2 = new javax.swing.JLabel();
        jButtonBuyTravelFirstClass = new javax.swing.JButton();
        jButtonBuyGun = new javax.swing.JButton();
        jLabelGunTerms2 = new javax.swing.JLabel();
        jLabelNiceClothesTerms1 = new javax.swing.JLabel();
        jLabelYakuzaTerms4 = new javax.swing.JLabel();
        jLabelYakuzaTerms2 = new javax.swing.JLabel();
        jLabelYakuzaTerms3 = new javax.swing.JLabel();
        jLabelGunTerms1 = new javax.swing.JLabel();
        jButtonBuyGenerous = new javax.swing.JButton();
        jLabelGenerousTerms1 = new javax.swing.JLabel();
        jLabelTravelFirstClassTerms2 = new javax.swing.JLabel();
        jButtonBuyNiceClothes = new javax.swing.JButton();
        jLabelYakuzaTerms1 = new javax.swing.JLabel();
        jLabelTravelFirstClassTerms1 = new javax.swing.JLabel();
        jLabelYakuzaTerms5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonPrintAllDrugPrices = new javax.swing.JButton();
        jButtonTestHighScore = new javax.swing.JButton();
        jButtonUpdatePlayerInventoryTest = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabelHighScoreHeadline = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableHighScore = new javax.swing.JTable();
        jButtonRestartGame = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelWelcomeText.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabelWelcomeText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelWelcomeText.setText("Welcome to Denmark!");
        jPanel1.add(jLabelWelcomeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 560, 34));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Buy drugs");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, 260, -1));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Sell drugs");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 85, 260, -1));

        jComboBoxBuyingDrugs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxBuyingDrugs.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxBuyingDrugsItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBoxBuyingDrugs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 260, -1));

        jComboBoxSellingDrugs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxSellingDrugs.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxSellingDrugsItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBoxSellingDrugs, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 110, 270, -1));

        jLabelCurrentMoney.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCurrentMoney.setText("Current money:");
        jPanel1.add(jLabelCurrentMoney, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 260, -1));

        jButtonBuyDrugs.setText("Buy drugs (0)");
        jButtonBuyDrugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuyDrugsActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonBuyDrugs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 260, -1));

        jSliderBuyDrugs.setMajorTickSpacing(10);
        jSliderBuyDrugs.setMaximum(60);
        jSliderBuyDrugs.setMinorTickSpacing(5);
        jSliderBuyDrugs.setPaintLabels(true);
        jSliderBuyDrugs.setPaintTicks(true);
        jSliderBuyDrugs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderBuyDrugsStateChanged(evt);
            }
        });
        jPanel1.add(jSliderBuyDrugs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 260, -1));

        jSliderSellDrugs.setMajorTickSpacing(10);
        jSliderSellDrugs.setMaximum(60);
        jSliderSellDrugs.setMinorTickSpacing(5);
        jSliderSellDrugs.setPaintLabels(true);
        jSliderSellDrugs.setPaintTicks(true);
        jSliderSellDrugs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderSellDrugsStateChanged(evt);
            }
        });
        jPanel1.add(jSliderSellDrugs, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 150, 280, -1));

        jButtonSellDrugs.setText("Sell drugs (0)");
        jButtonSellDrugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSellDrugsActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSellDrugs, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 210, 270, -1));

        jLabelCurrentTurn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCurrentTurn.setText("Current turn: ");
        jPanel1.add(jLabelCurrentTurn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 260, -1));

        jProgressBarCurrentHP.setForeground(new java.awt.Color(0, 204, 0));
        jProgressBarCurrentHP.setToolTipText("");
        jProgressBarCurrentHP.setValue(100);
        jProgressBarCurrentHP.setDoubleBuffered(true);
        jProgressBarCurrentHP.setString("100/100");
        jProgressBarCurrentHP.setStringPainted(true);
        jPanel1.add(jProgressBarCurrentHP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 265, 260, 21));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Current HP");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 245, 260, -1));

        jButtonBuyLife.setText("Buy life (10)");
        jButtonBuyLife.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuyLifeActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonBuyLife, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 250, 140, -1));

        jSliderBuyLife.setMajorTickSpacing(20);
        jSliderBuyLife.setMinorTickSpacing(10);
        jSliderBuyLife.setPaintLabels(true);
        jSliderBuyLife.setPaintTicks(true);
        jSliderBuyLife.setValue(0);
        jSliderBuyLife.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderBuyLifeStateChanged(evt);
            }
        });
        jPanel1.add(jSliderBuyLife, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 130, -1));

        jLabelLifeCost.setText("Life: $5000 pr. %");
        jPanel1.add(jLabelLifeCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 120, -1));

        jTabbedPane1.addTab("Drugs", jPanel1);
        jPanel1.getAccessibleContext().setAccessibleName("");

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxCountries.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBoxCountries, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 200, -1));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Go to country");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 12, 90, 20));

        jButtonTravel.setText("Travel");
        jButtonTravel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTravelActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonTravel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 9, 90, -1));

        jButtonHighFriendYakuza.setText("Buy Access into Yakuza ($20.000)");
        jButtonHighFriendYakuza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHighFriendYakuzaActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonHighFriendYakuza, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 250, -1));

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tools for traveling better");
        jLabel5.setToolTipText("");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 580, -1));

        jLabelNiceClothesTerms2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelNiceClothesTerms2.setText("3% improved chance of getting new helping girlfriend");
        jPanel2.add(jLabelNiceClothesTerms2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 244, 330, 20));

        jButtonBuyTravelFirstClass.setText("Travel 1st Class ($100.000)");
        jButtonBuyTravelFirstClass.setToolTipText("");
        jButtonBuyTravelFirstClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuyTravelFirstClassActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonBuyTravelFirstClass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 250, -1));

        jButtonBuyGun.setText("Buy Gun ($32.000)");
        jButtonBuyGun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuyGunActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonBuyGun, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 155, 250, -1));

        jLabelGunTerms2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelGunTerms2.setText("1% less chance of get caught trespassing mafia territory");
        jLabelGunTerms2.setToolTipText("");
        jPanel2.add(jLabelGunTerms2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 176, 330, 20));

        jLabelNiceClothesTerms1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelNiceClothesTerms1.setText("1% less chance of getting caught by custom");
        jLabelNiceClothesTerms1.setToolTipText("");
        jLabelNiceClothesTerms1.setDoubleBuffered(true);
        jPanel2.add(jLabelNiceClothesTerms1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 226, 330, 20));

        jLabelYakuzaTerms4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelYakuzaTerms4.setText("4% improved chance of getting new minion pusher ");
        jPanel2.add(jLabelYakuzaTerms4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 121, 330, 20));

        jLabelYakuzaTerms2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelYakuzaTerms2.setText("1% less chance of attacked by angry pusher");
        jLabelYakuzaTerms2.setToolTipText("");
        jPanel2.add(jLabelYakuzaTerms2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 87, 330, 20));

        jLabelYakuzaTerms3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelYakuzaTerms3.setText("2% less chance of get caught trespassing mafia territory");
        jLabelYakuzaTerms3.setToolTipText("");
        jPanel2.add(jLabelYakuzaTerms3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 104, 330, 20));

        jLabelGunTerms1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelGunTerms1.setText("2% less chance of attacked by angry pusher");
        jLabelGunTerms1.setToolTipText("");
        jPanel2.add(jLabelGunTerms1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 158, 330, 20));

        jButtonBuyGenerous.setText("Buy Generous ($40.000)");
        jButtonBuyGenerous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuyGenerousActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonBuyGenerous, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 196, 250, -1));

        jLabelGenerousTerms1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelGenerousTerms1.setText("1% less chance of attacked by angry pusher");
        jLabelGenerousTerms1.setToolTipText("");
        jPanel2.add(jLabelGenerousTerms1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 330, 20));

        jLabelTravelFirstClassTerms2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTravelFirstClassTerms2.setText("2% improved chance of getting new helping girlfriend");
        jPanel2.add(jLabelTravelFirstClassTerms2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 330, 20));

        jButtonBuyNiceClothes.setText("Buy Nice Clothes ($50.000)");
        jButtonBuyNiceClothes.setToolTipText("");
        jButtonBuyNiceClothes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuyNiceClothesActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonBuyNiceClothes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 224, 250, -1));

        jLabelYakuzaTerms1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelYakuzaTerms1.setText("2% less chance of getting caught by custom");
        jLabelYakuzaTerms1.setToolTipText("");
        jPanel2.add(jLabelYakuzaTerms1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 330, 20));

        jLabelTravelFirstClassTerms1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTravelFirstClassTerms1.setText("1% less chance of getting caught by custom");
        jLabelTravelFirstClassTerms1.setToolTipText("");
        jPanel2.add(jLabelTravelFirstClassTerms1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 264, 330, 20));

        jLabelYakuzaTerms5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelYakuzaTerms5.setText("3% improved chance of getting new helping girlfriend");
        jPanel2.add(jLabelYakuzaTerms5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 138, 330, 20));

        jTabbedPane1.addTab("Travel", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonPrintAllDrugPrices.setText("Print All Drug Prices");
        jButtonPrintAllDrugPrices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintAllDrugPricesActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonPrintAllDrugPrices, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 200, -1));

        jButtonTestHighScore.setText("Force Update High Score");
        jButtonTestHighScore.setToolTipText("");
        jButtonTestHighScore.setActionCommand("UpdateHighScore");
        jButtonTestHighScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestHighScoreActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonTestHighScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 270, -1));

        jButtonUpdatePlayerInventoryTest.setText("Force Update Models");
        jButtonUpdatePlayerInventoryTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdatePlayerInventoryTestActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonUpdatePlayerInventoryTest, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 270, -1));

        jTabbedPane1.addTab("Extra", jPanel3);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelHighScoreHeadline.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabelHighScoreHeadline.setText("High Scores in the Mafia Game");
        jPanel5.add(jLabelHighScoreHeadline, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 10, -1, -1));

        jTableHighScore.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Score"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableHighScore.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(jTableHighScore);
        jTableHighScore.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 580, 240));

        jButtonRestartGame.setText("Restart game");
        jButtonRestartGame.setToolTipText("");
        jButtonRestartGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRestartGameActionPerformed(evt);
            }
        });
        jPanel5.add(jButtonRestartGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jTabbedPane1.addTab("High Score", jPanel5);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 600, 350));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSliderBuyDrugsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderBuyDrugsStateChanged
        jButtonBuyDrugs.setText("Buy drugs (" + jSliderBuyDrugs.getValue() + ")");
    }//GEN-LAST:event_jSliderBuyDrugsStateChanged

    private void jComboBoxBuyingDrugsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxBuyingDrugsItemStateChanged
        System.out.println("[jComboBoxBuyingDrugsPropertyChange] Changed to item: " + getSelectedBuyingDrugName());
        int amount = mainGame.getCountryCurrentDrugAmount(getSelectedBuyingDrugName());
        jSliderBuyDrugs.setMaximum(amount);
        jSliderBuyDrugs.setValue(0);
    }//GEN-LAST:event_jComboBoxBuyingDrugsItemStateChanged

    private void jComboBoxSellingDrugsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxSellingDrugsItemStateChanged
        System.out.println("[jComboBoxSellingDrugsPropertyChange] Changed to item: " + getSelectedSellingDrugName());
        int amount = mainGame.getPlayerCurrentDrugAmount(getSelectedSellingDrugName());
        jSliderSellDrugs.setMaximum(amount);
    }//GEN-LAST:event_jComboBoxSellingDrugsItemStateChanged

    private void jSliderSellDrugsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderSellDrugsStateChanged
        jButtonSellDrugs.setText("Sell drugs (" + jSliderSellDrugs.getValue() + ")");
    }//GEN-LAST:event_jSliderSellDrugsStateChanged

    private void jButtonBuyDrugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuyDrugsActionPerformed
        String drugName = getSelectedBuyingDrugName();
        int amount = getSelectedBuyingDrugAmount();
        System.out.println("Buying " + amount + " " + drugName);
        if (amount > 0) {
            int price = mainGame.getCurrentCountry().getDrug(drugName).getPrice();
            try {
                mainGame.buyDrug(drugName, amount, price);
                updateModelBoxes();
            } catch (Exception e) {
                String msg = e.getMessage();
                System.out.println("Some error occurred---");
                JOptionPane.showMessageDialog(null, msg);
            }
        }
    }//GEN-LAST:event_jButtonBuyDrugsActionPerformed

    private void jButtonSellDrugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSellDrugsActionPerformed
        String drugName = getSelectedSellingDrugName();
        int amount = getSelectedSellingDrugAmount();
        if (!drugName.equals("null") && amount > 0) {
            try {
                mainGame.sellDrug(drugName, amount);
                updateModelBoxes();
            } catch (Exception e) {
                String msg = e.getMessage();
                JOptionPane.showMessageDialog(null, msg);
            }
            
        }
    }//GEN-LAST:event_jButtonSellDrugsActionPerformed

    private void jButtonUpdatePlayerInventoryTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdatePlayerInventoryTestActionPerformed
        updateModelBoxes();
    }//GEN-LAST:event_jButtonUpdatePlayerInventoryTestActionPerformed

    private void jButtonTravelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTravelActionPerformed
        String selectedCountry = jComboBoxCountries.getSelectedItem().toString();
        BaseCountry country = mainGame.getCountry(selectedCountry);
        mainGame.Travel(country);
        updateModelBoxes();
    }//GEN-LAST:event_jButtonTravelActionPerformed

    private void jButtonPrintAllDrugPricesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintAllDrugPricesActionPerformed
        ArrayList<BaseCountry> countries = mainGame.getCountries();
        for (int i = 0; i < countries.size(); i++) {
            BaseCountry c = countries.get(i);
            ArrayList<BaseDrug> drugs = c.getDrugs();
            System.out.println("---- " + c.getName() + " ----");
            for (int j = 0; j < drugs.size(); j++) {
                BaseDrug d = drugs.get(j);
                System.out.println(d.getName() + ", Price: " + d.getPrice() + ", Amount: " + d.getAmount());
            }
        }
    }//GEN-LAST:event_jButtonPrintAllDrugPricesActionPerformed

    private void jButtonTestHighScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestHighScoreActionPerformed
        updateHighScores();
    }//GEN-LAST:event_jButtonTestHighScoreActionPerformed

    private void jButtonRestartGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRestartGameActionPerformed
        this.dispose();
        try {
            mainGame = new MafiaGame();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButtonRestartGameActionPerformed

    private void jSliderBuyLifeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderBuyLifeStateChanged
        jButtonBuyLife.setText("Buy life (" + jSliderBuyLife.getValue() + ")");
    }//GEN-LAST:event_jSliderBuyLifeStateChanged

    private void jButtonBuyLifeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuyLifeActionPerformed
        int healthAmount = jSliderBuyLife.getValue();
        if (!mainGame.buyLife(healthAmount)) {
            JOptionPane.showMessageDialog(this, "Not enough money to buy " + healthAmount + "% health!");
        }
        updateModelBoxes();
    }//GEN-LAST:event_jButtonBuyLifeActionPerformed

    private void jButtonHighFriendYakuzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHighFriendYakuzaActionPerformed
        if (!mainGame.buyHighFriend(20000)) {
            JOptionPane.showMessageDialog(this, "Not enough money to buy your way into Yakuza.");
        }
        else {
            jButtonHighFriendYakuza.setEnabled(false);
            jButtonHighFriendYakuza.setText("You are friends with Yakuza");
            jLabelYakuzaTerms1.setForeground(java.awt.Color.red);
            jLabelYakuzaTerms2.setForeground(java.awt.Color.red);
            jLabelYakuzaTerms3.setForeground(java.awt.Color.red);
            jLabelYakuzaTerms4.setForeground(java.awt.Color.red);
            jLabelYakuzaTerms5.setForeground(java.awt.Color.red);
        }
        updateModelBoxes();
    }//GEN-LAST:event_jButtonHighFriendYakuzaActionPerformed

    private void jButtonBuyGunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuyGunActionPerformed
        if (!mainGame.buyGun(32000)) {
            JOptionPane.showMessageDialog(this, "Not enough money to buy a gun.");
        }
        else {
            jButtonBuyGun.setEnabled(false);
            jButtonBuyGun.setText("You have a gun");
            jLabelGunTerms1.setForeground(java.awt.Color.red);
            jLabelGunTerms2.setForeground(java.awt.Color.red);
        }
        updateModelBoxes();
    }//GEN-LAST:event_jButtonBuyGunActionPerformed

    private void jButtonBuyTravelFirstClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuyTravelFirstClassActionPerformed
        if (!mainGame.buyFirstClass(100000)) {
            JOptionPane.showMessageDialog(this, "Not enough money to buy first class tickets.");
        }
        else {
            jButtonBuyTravelFirstClass.setEnabled(false);
            jButtonBuyTravelFirstClass.setText("You are flying first class");
            jLabelTravelFirstClassTerms1.setForeground(java.awt.Color.red);
            jLabelTravelFirstClassTerms2.setForeground(java.awt.Color.red);
        }
        updateModelBoxes();
    }//GEN-LAST:event_jButtonBuyTravelFirstClassActionPerformed

    private void jButtonBuyGenerousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuyGenerousActionPerformed
        if (!mainGame.buyGenerous(40000)) {
            JOptionPane.showMessageDialog(this, "Not enough money to buy generous.");
        }
        else {
            jButtonBuyGenerous.setEnabled(false);
            jButtonBuyGenerous.setText("You are a generous person");
            jLabelGenerousTerms1.setForeground(java.awt.Color.red);
        }
        updateModelBoxes();
    }//GEN-LAST:event_jButtonBuyGenerousActionPerformed

    private void jButtonBuyNiceClothesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuyNiceClothesActionPerformed
        if (!mainGame.buyNiceClothes(50000)) {
            JOptionPane.showMessageDialog(this, "Not enough money to buy Nice Clothes.");
        }
        else {
            jButtonBuyNiceClothes.setEnabled(false);
            jButtonBuyNiceClothes.setText("You are looking good!");
            jLabelNiceClothesTerms1.setForeground(java.awt.Color.red);
            jLabelNiceClothesTerms2.setForeground(java.awt.Color.red);
        }
        updateModelBoxes();
    }//GEN-LAST:event_jButtonBuyNiceClothesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuyDrugs;
    private javax.swing.JButton jButtonBuyGenerous;
    private javax.swing.JButton jButtonBuyGun;
    private javax.swing.JButton jButtonBuyLife;
    private javax.swing.JButton jButtonBuyNiceClothes;
    private javax.swing.JButton jButtonBuyTravelFirstClass;
    private javax.swing.JButton jButtonHighFriendYakuza;
    private javax.swing.JButton jButtonPrintAllDrugPrices;
    private javax.swing.JButton jButtonRestartGame;
    private javax.swing.JButton jButtonSellDrugs;
    private javax.swing.JButton jButtonTestHighScore;
    private javax.swing.JButton jButtonTravel;
    private javax.swing.JButton jButtonUpdatePlayerInventoryTest;
    private javax.swing.JComboBox<String> jComboBoxBuyingDrugs;
    private javax.swing.JComboBox<String> jComboBoxCountries;
    private javax.swing.JComboBox<String> jComboBoxSellingDrugs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelCurrentMoney;
    private javax.swing.JLabel jLabelCurrentTurn;
    private javax.swing.JLabel jLabelGenerousTerms1;
    private javax.swing.JLabel jLabelGunTerms1;
    private javax.swing.JLabel jLabelGunTerms2;
    private javax.swing.JLabel jLabelHighScoreHeadline;
    private javax.swing.JLabel jLabelLifeCost;
    private javax.swing.JLabel jLabelNiceClothesTerms1;
    private javax.swing.JLabel jLabelNiceClothesTerms2;
    private javax.swing.JLabel jLabelTravelFirstClassTerms1;
    private javax.swing.JLabel jLabelTravelFirstClassTerms2;
    private javax.swing.JLabel jLabelWelcomeText;
    private javax.swing.JLabel jLabelYakuzaTerms1;
    private javax.swing.JLabel jLabelYakuzaTerms2;
    private javax.swing.JLabel jLabelYakuzaTerms3;
    private javax.swing.JLabel jLabelYakuzaTerms4;
    private javax.swing.JLabel jLabelYakuzaTerms5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBarCurrentHP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSliderBuyDrugs;
    private javax.swing.JSlider jSliderBuyLife;
    private javax.swing.JSlider jSliderSellDrugs;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableHighScore;
    // End of variables declaration//GEN-END:variables

}