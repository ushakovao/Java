/**
 * Created by ushakova on 19/07/16.
 */

import javax.swing.*;
import java.text.DecimalFormat;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.text.DecimalFormat;
public class BinomialTreeFrame extends JFrame {
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private double[] _columnNames;
    private String[] _sColumnNames;
    BinomialTreeFrame(String[][] _dataModel, int _iteration){
        super("Option Pricing: Binomial Tree");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _columnNames = new double[_iteration + 1];
        double temp1 = _iteration;
        DecimalFormat _dFormat = new DecimalFormat("##.####");
        for (int i=0; i < _iteration; i++) {
            if (i==0) {
                _columnNames[i] = 0;
            } else {
                double temp = Double.valueOf(_dFormat.format(1/temp1));
                _columnNames[i] = (temp) + _columnNames[i-1];
            }
        }
        _columnNames[_columnNames.length-1] = 1;
        _sColumnNames = new String[_columnNames.length];
        for (int i=0; i < _sColumnNames.length; i++) {
            _sColumnNames[i] = "" + _columnNames[i];
        }
        jTable1 = new JTable(_dataModel, _sColumnNames );
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1 = new JScrollPane(jTable1);
        jTable1.setFillsViewportHeight(true);
        //jScrollPane1.setHorizontalScrollBar(newJScrollBar(JScrollBar.HORIZONTAL, 0, 1, 0, 255));
        // this.getContentPane().
        add(jScrollPane1, BorderLayout.CENTER);
        // this.pack();
        this.setVisible(true);
    }
}