package gui;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class FileTextTable {
//	private TableView<FileText> table = new TableView<FileText>();
    private  ObservableList<FileText> data;
    ArrayList<FileText> als = new ArrayList<>();
    
    @SuppressWarnings("unchecked")
	public void setNumber(TableView<FileText> table, File file) {
    	@SuppressWarnings("rawtypes")
		TableColumn numberCol = new TableColumn("");
        numberCol.setMinWidth(20);
        TextFileReader reader = new TextFileReader();
		for (String s : reader.read(file)) {
			als.add(new FileText(s));
			System.out.println(s);
		}
        data = FXCollections.observableArrayList( als);
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<FileText, FileText>, ObservableValue<FileText>>() {
            @SuppressWarnings("rawtypes")
			public ObservableValue<FileText> call(CellDataFeatures<FileText, FileText> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });
        
        numberCol.setMinWidth(50);

        table.setStyle("-fx-table-cell-border-color: transparent;");
        numberCol.setCellFactory(new Callback<TableColumn<FileText, FileText>, TableCell<FileText, FileText>>() {
            @Override public TableCell<FileText, FileText> call(TableColumn<FileText, FileText> param) {
                return new TableCell<FileText, FileText>() {
                    @Override protected void updateItem(FileText item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null && item != null) {
                            setText(this.getTableRow().getIndex()+"");
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        
        numberCol.setSortable(false);

        @SuppressWarnings("rawtypes")
		TableColumn textCol = new TableColumn("");
        textCol.setMinWidth(100);
        textCol.setCellValueFactory(
                new PropertyValueFactory<FileText, String>("firstName"));

        textCol.setMinWidth(600);
        
        textCol.setSortable(false);
        

        table.setItems(data);
        table.getColumns().addAll(numberCol, textCol);
    }

	public ObservableList<FileText> getData() {
		return data;
	}
    
    
    

}
