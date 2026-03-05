/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Form;

import Konfigurasi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.awt.print.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ahmad
 */
public class FORM_TRANSAKSI extends javax.swing.JFrame {

    DefaultTableModel model;
    /**
     * Creates new form FORM_TRANSAKSI
     */
    public FORM_TRANSAKSI() {
     initComponents();
     model = new DefaultTableModel();
     model.addColumn("Kode");
     model.addColumn("Nama");
     model.addColumn("Harga");
     model.addColumn("Jumlah");
     model.addColumn("Subtotal");
     jTable_transaksi.setModel(model);
     
     tampilBarang();
    }

    private void tampilBarang() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Kode Barang");
    model.addColumn("Nama Barang");
    model.addColumn("Harga");
    model.addColumn("Stok");
    model.addColumn("Tanggal Masuk");

    try {
        String sql = "SELECT * FROM barang";
        Connection conn = Koneksi.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getInt("harga"),
                rs.getInt("stok"),
            });
        }

        jTable_daftar.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal tampil data: " + e.getMessage());
    }
}
    
    private String formatRupiah(double angka) {
    Locale localeID = new Locale("id", "ID");
    NumberFormat format = NumberFormat.getNumberInstance(localeID);
    format.setMaximumFractionDigits(0);
    return "Rp " + format.format(angka);
}



    
    private String terbilang(long angka) {
    String[] huruf = {"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam",
                      "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};

    if (angka < 12)
        return huruf[(int) angka];
    else if (angka < 20)
        return huruf[(int) (angka - 10)] + " Belas";
    else if (angka < 100)
        return huruf[(int) (angka / 10)] + " Puluh " + terbilang(angka % 10);
    else if (angka < 200)
        return "Seratus " + terbilang(angka - 100);
    else if (angka < 1000)
        return huruf[(int) (angka / 100)] + " Ratus " + terbilang(angka % 100);
    else if (angka < 2000)
        return "Seribu " + terbilang(angka - 1000);
    else if (angka < 1000000)
        return terbilang(angka / 1000) + " Ribu " + terbilang(angka % 1000);
    else if (angka < 1000000000)
        return terbilang(angka / 1000000) + " Juta " + terbilang(angka % 1000000);
    else
        return "Angka terlalu besar";
}
    
    private void tampilStruk(double total, double bayar, double kembalian) {

    StringBuilder struk = new StringBuilder();

    struk.append("========== TOKO KAEL ==========\n");
    struk.append("Tanggal : ").append(java.time.LocalDateTime.now()).append("\n");
    struk.append("--------------------------------\n");

    for (int i = 0; i < model.getRowCount(); i++) {

    String kode = model.getValueAt(i, 0).toString();
    String nama = model.getValueAt(i, 1).toString();

    // harga satuan (bersihin format Rp kalau ada)
    String hargaStr = model.getValueAt(i, 2).toString()
            .replace("Rp", "")
            .replace(".", "")
            .replace(",", "")
            .trim();
    double harga = Double.parseDouble(hargaStr);

    int jumlah = Integer.parseInt(model.getValueAt(i, 3).toString());

    String subStr = model.getValueAt(i, 4).toString()
            .replace("Rp", "")
            .replace(".", "")
            .replace(",", "")
            .trim();
    double subtotal = Double.parseDouble(subStr);

    struk.append(kode).append(" - ").append(nama).append("\n");
    struk.append("  ")
         .append(jumlah).append(" x ")
         .append(formatRupiah(harga))
         .append(" = ")
         .append(formatRupiah(subtotal))
         .append("\n");
}


    struk.append("--------------------------------\n");
    struk.append("Total     : ").append(formatRupiah(total)).append("\n");
    struk.append("Bayar     : ").append(formatRupiah(bayar)).append("\n");
    struk.append("Kembalian : ").append(formatRupiah(kembalian)).append("\n");
    struk.append("================================\n");
    struk.append("Terima kasih\n");

    try {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName("Struk Pembayaran");

        pj.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));

            int y = 15;
            for (String line : struk.toString().split("\n")) {
                g2d.drawString(line, 10, y);
                y += 15;
            }

            return Printable.PAGE_EXISTS;
        });

        pj.print();

    } catch (PrinterException e) {
        JOptionPane.showMessageDialog(this, "Gagal print: " + e.getMessage());
    }
}

    



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_cari = new javax.swing.JTextField();
        jButton_cari = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField_kodebarang = new javax.swing.JTextField();
        jTextField_namabarang = new javax.swing.JTextField();
        jTextField_harga = new javax.swing.JTextField();
        jComboBox_satuan = new javax.swing.JComboBox<>();
        jTextField_stok = new javax.swing.JTextField();
        jTextField_jumlahbeli = new javax.swing.JTextField();
        jButton_batal = new javax.swing.JButton();
        jButton_keluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_transaksi = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jTextField_total = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField_pembayaran = new javax.swing.JTextField();
        jTextField_kembalian = new javax.swing.JTextField();
        jButton_bayar = new javax.swing.JButton();
        jButton_kembali = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_daftar = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel_terbilang = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("TRANSAKSI PENJUALAN BARANG");

        jButton_cari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/search.png"))); // NOI18N
        jButton_cari.setText("Masukkan Kode Barang");
        jButton_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cariActionPerformed(evt);
            }
        });

        jLabel3.setText("Kode Barang");

        jLabel4.setText("Nama Barang");

        jLabel5.setText("Harga");

        jLabel6.setText("Satuan");

        jLabel7.setText("Stok");

        jLabel8.setText("Jumlah Beli");

        jComboBox_satuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unit", "Lusin", "Box" }));
        jComboBox_satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_satuanActionPerformed(evt);
            }
        });

        jTextField_jumlahbeli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_jumlahbeliFocusLost(evt);
            }
        });

        jButton_batal.setText("Batal");

        jButton_keluar.setText("Keluar");

        jTable_transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "KODE BARANG", "NAMA BARANG", "HARGA", "SATUAN", "STOK"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_transaksi);

        jLabel9.setText("TOTAL HARGA");

        jLabel10.setText("BAYAR");

        jLabel11.setText("KEMBALIAN");

        jTextField_kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_kembalianActionPerformed(evt);
            }
        });

        jButton_bayar.setBackground(new java.awt.Color(255, 51, 51));
        jButton_bayar.setText("PROSES BAYAR");
        jButton_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_bayarActionPerformed(evt);
            }
        });

        jButton_kembali.setText("Kembali");
        jButton_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_kembaliActionPerformed(evt);
            }
        });

        jTable_daftar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Harga", "Stok"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable_daftar);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/search.png"))); // NOI18N
        jButton1.setText("CARI BARANG");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel_terbilang.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel_terbilang.setText("\"\"");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField_cari)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_cari))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton_batal)
                                        .addGap(41, 41, 41)
                                        .addComponent(jButton_keluar)
                                        .addGap(40, 40, 40)
                                        .addComponent(jButton_kembali))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8))
                                        .addGap(35, 35, 35)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField_jumlahbeli, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_total, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel_terbilang, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_cari)
                        .addComponent(jTextField_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField_stok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField_jumlahbeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_batal)
                            .addComponent(jButton_keluar)
                            .addComponent(jButton_kembali))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_terbilang)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_bayar)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_kembalianActionPerformed

    private void jButton_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cariActionPerformed
        // TODO add your handling code here:
         Connection conn = Koneksi.getConnection();
    try {
        PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM barang WHERE kode_barang=?"
        );
        ps.setString(1, jTextField_cari.getText());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            jTextField_kodebarang.setText(rs.getString("kode_barang"));
            jTextField_namabarang.setText(rs.getString("nama_barang"));
            jTextField_harga.setText(rs.getString("harga"));
            jTextField_stok.setText(rs.getString("stok"));

            for (int i = 0; i < jComboBox_satuan.getItemCount(); i++) {
                if (jComboBox_satuan.getItemAt(i)
                        .equals(rs.getString("satuan"))) {
                    jComboBox_satuan.setSelectedIndex(i);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Barang tidak ditemukan");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
    jTextField_kodebarang.setEditable(false);
jTextField_namabarang.setEditable(false);
jTextField_harga.setEditable(false);
jTextField_stok.setEditable(false);
jComboBox_satuan.setEnabled(false);

    }//GEN-LAST:event_jButton_cariActionPerformed

    private void jButton_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_bayarActionPerformed
        // TODO add your handling code here:
    double total = Double.parseDouble(
    jTextField_total.getText()
        .replace("Rp", "")
        .replace(".", "")
        .replace(",", "")
        .trim()
);

double bayar = Double.parseDouble(
    jTextField_pembayaran.getText()
        .replace("Rp", "")
        .replace(".", "")
        .replace(",", "")
        .trim()
);

    if (bayar < total) {
        JOptionPane.showMessageDialog(this, "Uang bayar kurang!");
        return;
    }

    double kembalian = bayar - total;
    jTextField_kembalian.setText(formatRupiah(kembalian));


    DefaultTableModel model = (DefaultTableModel) jTable_transaksi.getModel();

    if (model.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Belum ada transaksi");
        return;
    }

    try {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/pos", "root", ""
        );

        // UPDATE STOK
        String updateStok = "UPDATE barang SET stok = stok - ? WHERE kode_barang = ?";
        PreparedStatement pstUpdate = conn.prepareStatement(updateStok);

        // INSERT HISTORY
        String insertHistory = "INSERT INTO history_transaksi "
                + "(tanggal, kode_barang, nama_barang, harga, satuan, jumlah, subtotal, total, bayar, kembalian) "
                + "VALUES (NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstInsert = conn.prepareStatement(insertHistory);

        for (int i = 0; i < model.getRowCount(); i++) {

            String kode = model.getValueAt(i, 0).toString();
            String nama = model.getValueAt(i, 1).toString();
            double harga = Double.parseDouble(model.getValueAt(i, 2).toString());
            int jumlah = Integer.parseInt(model.getValueAt(i, 3).toString());
            double subtotal = Double.parseDouble(model.getValueAt(i, 4).toString());

            // Update stok
            pstUpdate.setInt(1, jumlah);
            pstUpdate.setString(2, kode);
            pstUpdate.executeUpdate();

            // Insert history
            pstInsert.setString(1, kode);
            pstInsert.setString(2, nama);
            pstInsert.setDouble(3, harga);
            pstInsert.setString(4, "UNIT"); // kalau mau ambil dari database bisa nanti
            pstInsert.setInt(5, jumlah);
            pstInsert.setDouble(6, subtotal);
            pstInsert.setDouble(7, total);
            pstInsert.setDouble(8, bayar);
            pstInsert.setDouble(9, kembalian);

            pstInsert.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Pembayaran berhasil & tersimpan di history");

        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }

    tampilStruk(total, bayar, kembalian);
    resetForm();
   

    }//GEN-LAST:event_jButton_bayarActionPerformed

    private void jButton_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_kembaliActionPerformed
        // TODO add your handling code here:
        new FORM_MENU2().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton_kembaliActionPerformed

    private void jTextField_jumlahbeliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_jumlahbeliFocusLost
        // TODO add your handling code here:
        double harga = Double.parseDouble(jTextField_harga.getText());
        double stok  = Double.parseDouble(jTextField_stok.getText());
        int jumlah   = Integer.parseInt(jTextField_jumlahbeli.getText());

        if (jumlah > stok) {
            JOptionPane.showMessageDialog(this, "Stok tidak cukup");
            return;
        }

        double subtotal = harga * jumlah;

        model.addRow(new Object[]{
            jTextField_kodebarang.getText(),
            jTextField_namabarang.getText(),
            harga,
            jumlah,
            subtotal
        });

        hitungTotal();

    }//GEN-LAST:event_jTextField_jumlahbeliFocusLost

    private void jComboBox_satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_satuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_satuanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         String keyword = jTextField1.getText();

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Kode Barang");
    model.addColumn("Nama Barang");
    model.addColumn("Harga");
    model.addColumn("Stok");
    model.addColumn("Tanggal Masuk");

    try {
        Connection conn = Koneksi.getConnection();

        String sql = "SELECT * FROM barang WHERE kode_barang LIKE ? OR nama_barang LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getInt("harga"),
                rs.getInt("stok"),
            });
        }

        jTable_daftar.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void hitungTotal() {
    double total = 0;

    for (int i = 0; i < model.getRowCount(); i++) {
        total += Double.parseDouble(
            model.getValueAt(i, 4).toString()
        );
    }

    jTextField_total.setText(formatRupiah(total));


    long totalLong = (long) total;
    String hasilTerbilang = terbilang(totalLong) + " Rupiah";
    jLabel_terbilang.setText(hasilTerbilang);
}

    private void resetForm() {
    // kosongkan textfield
    jTextField_kodebarang.setText("");
    jTextField_namabarang.setText("");
    jTextField_harga.setText("");
    jTextField_stok.setText("");
    jTextField_jumlahbeli.setText("");
    jTextField_total.setText("");
    jTextField_pembayaran.setText("");
    jTextField_kembalian.setText("");
    jTextField_cari.setText("");

    // aktifkan kembali field yang tadi dikunci
    jTextField_kodebarang.setEditable(true);
    jTextField_namabarang.setEditable(false); // kalau pakai cari
    jTextField_harga.setEditable(false);
    jTextField_stok.setEditable(false);

    // kosongkan tabel transaksi
    DefaultTableModel model = (DefaultTableModel) jTable_transaksi.getModel();
    model.setRowCount(0);

    // fokus balik ke kode barang
    jTextField_kodebarang.requestFocus();
}


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FORM_TRANSAKSI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FORM_TRANSAKSI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FORM_TRANSAKSI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FORM_TRANSAKSI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FORM_TRANSAKSI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_batal;
    private javax.swing.JButton jButton_bayar;
    private javax.swing.JButton jButton_cari;
    private javax.swing.JButton jButton_keluar;
    private javax.swing.JButton jButton_kembali;
    private javax.swing.JComboBox<String> jComboBox_satuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_terbilang;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_daftar;
    private javax.swing.JTable jTable_transaksi;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField_cari;
    private javax.swing.JTextField jTextField_harga;
    private javax.swing.JTextField jTextField_jumlahbeli;
    private javax.swing.JTextField jTextField_kembalian;
    private javax.swing.JTextField jTextField_kodebarang;
    private javax.swing.JTextField jTextField_namabarang;
    private javax.swing.JTextField jTextField_pembayaran;
    private javax.swing.JTextField jTextField_stok;
    private javax.swing.JTextField jTextField_total;
    // End of variables declaration//GEN-END:variables
}
