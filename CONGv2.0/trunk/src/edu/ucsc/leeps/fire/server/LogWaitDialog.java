/**
 * Copyright (c) 2012, University of California
 * All rights reserved.
 * 
 * Redistribution and use is governed by the LICENSE.txt file included with this
 * source code and available at http://leeps.ucsc.edu/cong/wiki/license
 **/

/*
 * EndSessionDialog.java
 *
 * Created on Dec 15, 2010, 11:06:41 AM
 */
package edu.ucsc.leeps.fire.server;

import edu.ucsc.leeps.fire.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JFrame;

/**
 *
 * @author jpettit
 */
public class LogWaitDialog extends javax.swing.JDialog implements ActionListener {

    /** Creates new form EndSessionDialog */
    public LogWaitDialog(java.awt.Frame parent, boolean modal, Logger logger) {
        super(parent, modal);
        initComponents();

        progressBar.setMinimum(0);
        progressBar.setMaximum(logger.queueSize());
        logger.setQueueListener(this);

        setTitle("Writing Log File");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        warningLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        warningLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        warningLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        warningLabel.setText("Writing log file");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, warningLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(progressBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .add(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(warningLabel)
                .add(21, 21, 21)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables

    public void actionPerformed(ActionEvent ae) {
        Collection c = (Collection) ae.getSource();
        progressBar.setValue(progressBar.getMaximum() - c.size());
        if (c.isEmpty()) {
            dispose();
        }
    }
}
