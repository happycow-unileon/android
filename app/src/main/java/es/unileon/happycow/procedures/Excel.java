package es.unileon.happycow.procedures;

import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.Category;
import es.unileon.happycow.handler.IdCategory;
import java.io.File;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.model.User;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.model.facade.InterfaceEvaluationModel;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.CellView;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

/**
 *
 * @author amdiaz8
 */
public class Excel {

    private WritableWorkbook workbook;
    private WritableSheet actualSheet;

    private WritableCellFormat redBackground;
    private WritableCellFormat blueBackground;
    private WritableCellFormat yellowBackground;
    private WritableCellFormat goldBackground;

    public Excel(File file) {
        try {
            workbook = Workbook.createWorkbook(file);
            createColors();
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createColors() {
        redBackground = new WritableCellFormat();
        blueBackground = new WritableCellFormat();
        yellowBackground = new WritableCellFormat();
        goldBackground = new WritableCellFormat();
        

        try {
            redBackground.setBackground(Colour.RED);
            blueBackground.setBackground(Colour.PERIWINKLE);
            yellowBackground.setBackground(Colour.YELLOW);
            goldBackground.setBackground(Colour.GOLD);
        } catch (WriteException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean exportDataUsers(LinkedHashMap<User, LinkedList<Farm>> list) {
        int numberUser = 0;
        int row = 1;
        try {
            for (User user : list.keySet()) {

                actualSheet = workbook.createSheet(user.getName(), numberUser);
                writeHeader();
                row = exportDataFarm(row, list.get(user));
                if (row < 0) {
                    return false;
                }
                numberUser++;
                row++;
                resizeCells();
            }
        } catch (ExcelException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            WritableCell c = actualSheet.getWritableCell(0 ,18);
                WritableCellFormat newFormat = new WritableCellFormat();
                try {
                    newFormat.setBackground(Colour.BLUE_GREY);
                } catch (WriteException ex) {
                    System.out.println(ex.toString());
                    Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
                }
                c.setCellFormat(newFormat);
                
                
            workbook.write();
            workbook.close();
        } catch (IOException | WriteException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private void resizeCells() {
        for (int x = 0; x < ColumnExcel.values().length; x++) {
            CellView cell = actualSheet.getColumnView(x);
            cell.setAutosize(true);
            actualSheet.setColumnView(x, cell);
        }
    }

    private void writeHeader() throws ExcelException {
        for (ColumnExcel columnExcel : ColumnExcel.values()) {

            Label name = new Label(columnExcel.ordinal(), 0,
                    ColumnExcel.getName(columnExcel), redBackground);
            try {
                actualSheet.addCell(name);
            } catch (WriteException ex) {
                throw new ExcelException("Error escribiendo la granja");
            }
        }
    }

    private void paintCells(int row, int init, int end, WritableCellFormat format) {
        for (int i = init; i < end; i++) {
            WritableCell c = actualSheet.getWritableCell(i, row);
            CellFormat oldFormat = c.getCellFormat();
            if (oldFormat != null) {
                WritableCellFormat newFormat = new WritableCellFormat(oldFormat);
                try {
                    newFormat.setBackground(format.getBackgroundColour());
                } catch (WriteException ex) {
                    Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
                }
                c.setCellFormat(newFormat);
            }else{
                WritableCellFormat newFormat = new WritableCellFormat();
                try {
                    newFormat.setBackground(format.getBackgroundColour());
                } catch (WriteException ex) {
                    System.out.println(ex.toString());
                    Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
                }
                c.setCellFormat(newFormat);
            }
//            if (oldFormat == null) {
//                newFormat = new WritableCellFormat();
//                try {
//                    newFormat.setBackground(format.getBackgroundColour());
//                } catch (WriteException ex) {
//                    Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                c.setCellFormat(newFormat);
//            } else {
//                newFormat = new WritableCellFormat(oldFormat);
//                try {
//                    newFormat.setBackground(format.getBackgroundColour());
//                } catch (WriteException ex) {
//                    Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }
    }

    private int exportDataFarm(int row, LinkedList<Farm> list) throws ExcelException {
        for (Farm farm : list) {
            row = writeFarm(row, farm);
            row++;
        }
        return row;
    }

    private int writeFarm(int row, Farm farm) throws ExcelException {

        Label name = new Label(ColumnExcel.FARM_NAME.ordinal(), row,
                farm.getFarmName(), yellowBackground);
//        jxl.write.Number number = new jxl.write.Number(ColumnExcel.COW_NUMBER.ordinal(),
//                row, farm.getCowNumber());
        try {
            actualSheet.addCell(name);
//            actualSheet.addCell(number);
        } catch (WriteException ex) {
            throw new ExcelException("Error escribiendo la granja");
        }

        paintCells(row, ColumnExcel.FARM_NAME.ordinal(), ColumnExcel.values().length, yellowBackground);
        row++;
        for (InterfaceEvaluationModel interfaceEvaluationModel : Database.getInstance(null).getListEvaluations(farm.getIdUser(), farm.getIdFarm())) {

            row = writeEvaluation(row, Database.getInstance(null).getEvaluation(interfaceEvaluationModel.getIdHandler()));
            row++;
        }

        return row;
    }

    private int writeEvaluation(int row, InterfaceEvaluationModel evaluation) throws ExcelException {
        jxl.write.Number number = new jxl.write.Number(ColumnExcel.COW_NUMBER.ordinal(),
                row, evaluation.getInformation().getNumberCows());
        //escribir fecha evaluacion
        DateFormat customDateFormat = new DateFormat("dd MMM yyyy");

        WritableCellFormat dateFormat = new WritableCellFormat(customDateFormat);
        DateTime dateCell = new DateTime(ColumnExcel.EVALUATION_DATE.ordinal(),
                row, evaluation.getInformation().getFecha(), dateFormat);

        try {
            actualSheet.addCell(number);
            actualSheet.addCell(dateCell);
        } catch (WriteException ex) {
            throw new ExcelException("Error escribiendo la evaluación");
        }

        //coloreo la celda
        paintCells(row, ColumnExcel.EVALUATION_DATE.ordinal(), ColumnExcel.values().length, blueBackground);
        row++;
        for (Category category : Category.values()) {
            row = writeCategory(row, category, evaluation);
        }

        return row;
    }

    private int writeCategory(int row, Category category, InterfaceEvaluationModel evaluation) throws ExcelException {
        //escribir la categoria y su ponderacion
        Label name = new Label(ColumnExcel.CATEGORY.ordinal(), row,
                Category.getName(category));
        jxl.write.Number number = new jxl.write.Number(ColumnExcel.PONDERATION_CATEGORY.ordinal(),
                row, evaluation.getWeighing(new IdCategory(category)));
        try {
            actualSheet.addCell(name);
            actualSheet.addCell(number);
        } catch (WriteException ex) {
            throw new ExcelException("Error escribiendo la categoria");
        }
        paintCells(row, ColumnExcel.CATEGORY.ordinal(), ColumnExcel.values().length, goldBackground);
        LinkedList<Criterion> criterions = evaluation.getListCriterion(category);
        for (Criterion criterion : criterions) {
            row = writeCriterion(row, evaluation, criterion);
        }
        return row;
    }

    private int writeCriterion(int row,
            InterfaceEvaluationModel evaluation, Criterion criterion) throws ExcelException {
        LinkedList<Valoration> valorations = evaluation.listOfCriterion(criterion.getId());
        //escribir criterio y su ponderacion
        Label name = new Label(ColumnExcel.CRITERION.ordinal(), row,
                criterion.getName());
        jxl.write.Number number = new jxl.write.Number(ColumnExcel.PONDERATION_CRITERION.ordinal(),
                row, criterion.getWeighing());

        try {
            actualSheet.addCell(name);
            actualSheet.addCell(number);
        } catch (WriteException ex) {
            throw new ExcelException("Error escribiendo el criterio");
        }
        
        int rowBackup=row;
        for (Valoration valoration : valorations) {
            writeValoration(row, valoration);
            row++;
        }
        paintCells(rowBackup, ColumnExcel.CRITERION.ordinal(), ColumnExcel.values().length, blueBackground);
        return row;
    }

    private int writeValoration(int row, Valoration valoration) throws ExcelException {
        //escribir valoracion
        jxl.write.Number number = new jxl.write.Number(ColumnExcel.VALORATION.ordinal(),
                row, valoration.getNota());

        try {
            actualSheet.addCell(number);
        } catch (WriteException ex) {
            throw new ExcelException("Error escribiendo la valoración");
        }
        return row;
    }

}
