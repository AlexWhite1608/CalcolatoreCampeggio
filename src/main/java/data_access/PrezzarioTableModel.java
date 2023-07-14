package data_access;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class PrezzarioTableModel implements TableModel {
    private String[] columnNames = {"Tipologia", "Bassa stagione", "Alta stagione"};
    private String[][] data;

    public PrezzarioTableModel(ArrayList<ArrayList<String>> values) {
        extractData(values);
    }

    //FIXME: ottimizza questi cicli for
    private void extractData(ArrayList<ArrayList<String>> values) {
        int columns = columnNames.length;
        int rows = values.get(0).size();

        data = new String[rows][columnNames.length];

        ArrayList<String[]> tmp = new ArrayList<>();
        ArrayList<String> row = values.get(0);
        for(int i = 0; i < rows; i++) {
            String[] split = row.get(i).split(";");
            tmp.add(split);
        }

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                data[i][j] = tmp.get(i)[j];
            }
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
