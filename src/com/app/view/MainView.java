package com.app.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JTextArea inputTextArea;
    private JScrollPane inputScroll;
    private DefaultTableModel tableModel;
    private JButton analyzeButton;
    private JButton themeToggleButton;
    private boolean isDarkMode = true;

    public MainView() {
        configureWindow();
        initComponents();
    }

    private void configureWindow() {
        setTitle("PR03. Regex Programming");
        setSize(900, 900);
        setMinimumSize(new Dimension(900, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initComponents() {
        JPanel topHeaderPanel = new JPanel(new BorderLayout());
        topHeaderPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

        JLabel titleLabel = new JLabel("Regex Analyzer");
        titleLabel.setFont(new Font("Iosevka", Font.BOLD, 20));

        themeToggleButton = new JButton("Light Mode");
        themeToggleButton.setFont(new Font("Iosevka", Font.PLAIN, 14));
        themeToggleButton.setFocusPainted(false);
        themeToggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        themeToggleButton.setPreferredSize(new Dimension(140, 35));
        themeToggleButton.addActionListener(_ -> toggleTheme());

        topHeaderPanel.add(titleLabel, BorderLayout.WEST);
        topHeaderPanel.add(themeToggleButton, BorderLayout.EAST);
        add(topHeaderPanel, BorderLayout.NORTH);

        // INPUT Module
        inputTextArea = new JTextArea();
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputTextArea.setFont(new Font("Iosevka", Font.PLAIN, 16));
        inputTextArea.setMargin(new Insets(10, 10, 10, 10));

        inputTextArea.setText("""
                En el último trimestre del año 2024, la empresa reportó ingresos por 5,000,000.50 dólares, lo
                que representa un aumento del 7.8% en comparación con el mismo período del año anterior. El
                número de productos vendidos alcanzó los 120,000, con un promedio de 500 unidades por día.
                La tasa de retorno de inversión fue del 15.2%, superando las expectativas iniciales del 10%.
                Además, se estableció una meta de crecimiento del 20% para el próximo año, proyectando ventas
                de más de 6 millones de unidades. Los costos operativos aumentaron un 3.5%, pero el margen de
                ganancias sigue siendo saludable, con un 25%. En cuanto a la eficiencia energética, la planta
                redujo el consumo de electricidad en un 12.4%, lo que se traduce en un ahorro de
                aproximadamente $150,000 anuales. En el departamento de marketing, el presupuesto se
                incrementó en un 8%, alcanzando los $1,200,000 para cubrir campañas internacionales. La
                empresa también planea abrir 10 nuevas tiendas, lo que generará alrededor de 500 empleos. La
                duración promedio de los proyectos fue de 8.5 meses, lo que demuestra un considerable avance
                en la optimización de los procesos. Los números de atención al cliente mostraron una mejora del
                4.3%, con más de 95,000 consultas resueltas. En el último mes, el precio de las acciones subió
                un 2.5%, alcanzando un valor de $75.25 por acción. Finalmente, el porcentaje de satisfacción de
                los empleados ha mejorado al 89.7%, un reflejo del esfuerzo por mantener un entorno laboral
                saludable.""");

        inputTextArea.setCaretPosition(0);

        inputScroll = new JScrollPane(inputTextArea);
        TitledBorder inputBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5), "Entry Module");
        inputBorder.setTitleFont(new Font("Iosevka", Font.BOLD, 16));
        inputScroll.setBorder(inputBorder);

        analyzeButton = new JButton("Analyze Text");
        analyzeButton.setFont(new Font("Iosevka", Font.BOLD, 16));
        analyzeButton.setBackground(new Color(70, 170, 95));
        analyzeButton.setForeground(Color.WHITE);
        analyzeButton.setFocusPainted(false);
        analyzeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(analyzeButton);

        // RESULTS Module
        String[] columns = {"No.", "Line No.", "String", "Type"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable resultsTable = createResultsTable();

        JScrollPane resultsScroll = new JScrollPane(resultsTable);
        TitledBorder resultsBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5), "Results Module");
        resultsBorder.setTitleFont(new Font("Iosevka", Font.BOLD, 16));
        resultsScroll.setBorder(resultsBorder);

        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.add(buttonPanel, BorderLayout.NORTH);
        lowerPanel.add(resultsScroll, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputScroll, lowerPanel);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(splitPane, BorderLayout.CENTER);
    }

    private JTable createResultsTable() {
        JTable table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    Color altColor = UIManager.getColor("Table.alternateRowColor");
                    if (altColor == null) {
                        Color bg = getBackground();
                        int offset = isDarkMode ? 12 : -12;
                        altColor = new Color (
                                Math.clamp(bg.getRed() + offset, 0, 255),
                                Math.clamp(bg.getGreen() + offset, 0, 255),
                                Math.clamp(bg.getBlue() + offset, 0, 255)
                        );
                    }
                    comp.setBackground(row % 2 == 1 ? altColor : getBackground());
                }
                return comp;
            }
        };
        table.setFillsViewportHeight(true);
        table.setRowHeight(36);
        table.setFont(new Font("Iosevka", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Iosevka", Font.BOLD, 16));
        return table;
    }

    private void toggleTheme() {
        try {
            int currentScrollPosition = inputScroll.getVerticalScrollBar().getValue();
            if (isDarkMode) {
                FlatLightLaf.setup();
                themeToggleButton.setText("Dark Mode");
            } else {
                FlatDarkLaf.setup();
                themeToggleButton.setText("Light Mode");
            }
            isDarkMode = !isDarkMode;
            FlatLaf.updateUI();

            SwingUtilities.invokeLater(() ->
                    inputScroll.getVerticalScrollBar().setValue(currentScrollPosition)
            );
        } catch (Exception ex) {
            System.err.println("Failed to switch theme: " + ex.getMessage());
        }
    }

    public String getInputText() {
        return inputTextArea.getText();
    }

    public void setAnalyzeButtonListener(ActionListener listener) {
        analyzeButton.addActionListener(listener);
    }

    public void clearResults() {
        tableModel.setRowCount(0);
    }

    public void addResultRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void displayWarning(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
