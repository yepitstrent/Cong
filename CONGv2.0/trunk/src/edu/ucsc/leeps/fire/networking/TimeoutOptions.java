/**
 * Copyright (c) 2012, University of California
 * All rights reserved.
 * 
 * Redistribution and use is governed by the LICENSE.txt file included with this
 * source code and available at http://leeps.ucsc.edu/cong/wiki/license
 **/

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TimeoutOptions.java
 *
 * Created on Aug 17, 2011, 5:29:54 PM
 */
package edu.ucsc.leeps.fire.networking;

import javax.swing.JOptionPane;

/**
 *
 * @author Joe
 */
public class TimeoutOptions extends javax.swing.JFrame {

    public String choice;
    /** Creates new form TimeoutOptions */
    public TimeoutOptions() {
        initComponents();
        choice = "none";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        autoButton = new javax.swing.JButton();
        findButton = new javax.swing.JButton();
        findField = new javax.swing.JTextField();
        quitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        autoButton.setText("Auto");
        autoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoButtonActionPerformed(evt);
            }
        });

        findButton.setText("Find");
        findButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButtonActionPerformed(evt);
            }
        });

        findField.setText("IP Address");
        findField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findFieldActionPerformed(evt);
            }
        });

        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("It is taking longer than usual to find the server.");

        jLabel2.setText("Would you like to continue auto-discovery, or enter an IP address?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(autoButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(findButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(findField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quitButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(autoButton)
                    .addComponent(findButton)
                    .addComponent(findField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quitButton))
                .addGap(67, 67, 67))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        choice = "quit";
        dispose();
    }//GEN-LAST:event_quitButtonActionPerformed

    private void findFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findFieldActionPerformed
        if (validIP(findField.getText())) {
            choice = "find";
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid IP address.",
                    "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_findFieldActionPerformed

    private void autoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoButtonActionPerformed
        choice = "auto";
        dispose();
    }//GEN-LAST:event_autoButtonActionPerformed

    private void findButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButtonActionPerformed
                if (validIP(findField.getText())) {
            choice = "find";
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid IP address.",
                    "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_findButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TimeoutOptions().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton autoButton;
    private javax.swing.JButton findButton;
    public javax.swing.JTextField findField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton quitButton;
    // End of variables declaration//GEN-END:variables

    public static boolean validIP(String string) {
        char[] chars = string.toCharArray();
        System.err.println("Number of chars: " + chars.length);
        for (char c : chars) {
            if (!Character.isDigit(c) && c != '.') return false;
        }
        return true;
    }
}
