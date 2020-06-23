package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;


public class Main extends Application {

    StackPane option;
    StackPane rightpart;
    String textOption = "Admin Login";
    ResultSet rs;
    int curJudge = 0;


    ObservableList<Person> tableList;

    public void updateList(Connection con){
        try{
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("select * from team");
            ArrayList<Person> list = new ArrayList<Person>();
            while(rset.next()){
                list.add(new Person(rset.getInt(1), rset.getInt(3), rset.getInt(4), rset.getInt(6), rset.getInt(7), rset.getInt(8), rset.getString(2), rset.getString(5)));
            }
            tableList = FXCollections.observableArrayList();
            tableList.addAll(list);
        }catch (Exception e){
            System.out.println(e);
        }
    }

//    public void setTableContent() {
//        ObservableList<Person> data = FXCollections.<Person>observableArrayList();
//        for(Person p : list){
//            data.add(FXCollections.observableArrayList(p));
//        }
//
//        tableBookList.setItems(data);
//    }

    @Override
    public void start(Stage stage) throws Exception{

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/doe?autoReconnect=true&useSSL=false","debian-sys-maint","TsFGpziP0rsLMWE5");
            Statement stmt = con.createStatement();
//            ResultSet rs=stmt.executeQuery("select * from judge");
//            while(rs.next())
//                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

            HBox fileroot = new HBox();
            VBox menu = new VBox();
            option = new StackPane();



            // Table Event
            TableView table = new TableView();
            TableColumn tableId = new TableColumn("Team Id");
            tableId.setMinWidth(100);
            tableId.setCellValueFactory(
                    new PropertyValueFactory<Person, Integer>("id"));
            TableColumn tablename = new TableColumn("Team Name");
            tablename.setMinWidth(100);
            tablename.setCellValueFactory(
                    new PropertyValueFactory<Person, String>("nameTeam"));
            TableColumn tableMen = new TableColumn("Members");
            tableMen.setMinWidth(100);
            tableMen.setCellValueFactory(
                    new PropertyValueFactory<Person, Integer>("member"));
            TableColumn tableBatch = new TableColumn("Batch Year");
            tableBatch.setMinWidth(100);
            tableBatch.setCellValueFactory(
                    new PropertyValueFactory<Person, Integer>("batch"));
            TableColumn tablePerf = new TableColumn("Performance Type");
            tablePerf.setMinWidth(100);
            tablePerf.setCellValueFactory(
                    new PropertyValueFactory<Person, String>("perfType"));
            TableColumn tableJudge1 = new TableColumn("Judge1");
            tableJudge1.setMinWidth(100);
            tableJudge1.setCellValueFactory(
                    new PropertyValueFactory<Person, Integer>("judge1"));
            TableColumn tableJudge2 = new TableColumn("Judge2");
            tableJudge2.setMinWidth(100);
            tableJudge2.setCellValueFactory(
                    new PropertyValueFactory<Person, Integer>("judge2"));
            TableColumn tableJudge3 = new TableColumn("Judge3");
            tableJudge3.setMinWidth(100);
            tableJudge3.setCellValueFactory(
                    new PropertyValueFactory<Person, Integer>("judge3"));

            updateList(con);
            
            table.getColumns().addAll(tableId, tablename, tableMen, tableBatch, tablePerf, tableJudge1, tableJudge2, tableJudge3);
//            table.getItems().add(list);
//            tableList = FXCollections.observableArrayList();
//            for (Person p :
//                    list) {
//                System.out.println(p.getId());
//            }
//            tableList.addAll(list);
            table.setItems(tableList);
//            table.getItems().add(tableList);
            option.getChildren().clear();
            option.getChildren().add(table);
//            for (Person p :
//                    list) {
//                System.out.println(p.getBatch());
//            }
            // Insert Score
            Label TeamName = new Label("Team Id");
            TextField tf1Team = new TextField();
            Label marks = new Label("Marks");
            TextField tf1Marks = new TextField();
            Button SubmitMarks=new Button ("Marks Submit");
            SubmitMarks.setOnAction(e -> {
                try{
                    int teamid = Integer.parseInt(tf1Team.getText());
                    int score = Integer.parseInt(tf1Marks.getText());
                    System.out.println(teamid);
                    System.out.println(score);
                    System.out.println(curJudge);
                    if(curJudge == 1){
                        stmt.executeUpdate("update team set judge1='" + score + "' where tid ='" + teamid + "'");
                        System.out.println("complete");
                    }
                    else if(curJudge == 2){
                        stmt.executeUpdate("update team set judge2='" + score + "' where tid ='" + teamid + "'");
                        System.out.println("complete");
                    }
                    else if(curJudge == 3){
                        System.out.println(curJudge);
                        stmt.executeUpdate("update team set judge3='" + score + "' where tid ='" + teamid + "'");
                        System.out.println("complete");
                    }
                    else{
                        System.out.println("Error Ocured");
                        return;
                    }
                }catch(Exception ex){

                }
            });
            GridPane submitJudge=new GridPane();
            submitJudge.addRow(0, TeamName,tf1Team);
            submitJudge.addRow(1, marks,tf1Marks);
            submitJudge.addRow(2, SubmitMarks);

            //Add Event Team
            Label nameTeam=new Label("Team name");
            Label member=new Label("Number of Member");
            Label batch = new Label("Batch");
            Label perfType = new Label("Performance Type ");
            TextField tf1name=new TextField();
            TextField tf1mem=new TextField();
            TextField tf1Batch = new TextField();
            TextField tf1perf = new TextField();
            Button SubmitEvent=new Button ("Submit Event");
            SubmitEvent.setOnAction(e -> {
                String name = tf1name.getText();
                int mem = Integer.parseInt(tf1mem.getText());
                int batchYear = Integer.parseInt(tf1Batch.getText());
                String Perf = tf1perf.getText();

                try {
                    stmt.executeUpdate("insert into team(nameTeam, nummember, batch, perftype) values ('" + name + "','" + mem + "', '" + batchYear + "', '" + Perf + "')");
                    System.out.println("Inserted");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            GridPane adminEvent=new GridPane();

            adminEvent.addRow(0, nameTeam,tf1name);
            adminEvent.addRow(1, member,tf1mem);
            adminEvent.addRow(2, batch, tf1Batch);
            adminEvent.addRow(3, perfType, tf1perf);
            adminEvent.addRow(4, SubmitEvent);

            // Judge Login
            Label nameJudge=new Label("First Name");
            Label passJudge=new Label("Password");
            TextField tf1Judge=new TextField();
            TextField tf2Judge=new TextField();
            Button SubmitJudge=new Button ("Judge Submit");
            SubmitJudge.setOnAction(e -> {
                String name = tf1Judge.getText();
                String pass = tf2Judge.getText();

                try {
                    rs = stmt.executeQuery("select * from judge where name = '" + name + "' and pass = '" + pass + "'");
                    int flag = 1;
                    while(rs.next()){
                        curJudge = rs.getInt(1);
                        flag = 0;
                    }
                    if(flag == 1){
                        System.out.println("Wrong Credential");
                        return;
                    }
                    if(flag == 0){
                        System.out.println("curJudge " + curJudge);
                        option.getChildren().clear();
                        option.getChildren().add(submitJudge);
                    }
                }catch (Exception ex){
                    System.out.println(ex);
                }
            });
            GridPane judgeLogin=new GridPane();
            judgeLogin.addRow(0, nameJudge,tf1Judge);
            judgeLogin.addRow(1, passJudge,tf2Judge);
            judgeLogin.addRow(2, SubmitJudge);

            //Delete Event Team
            Label teamId=new Label("Team Id");
            TextField tf1Id=new TextField();
            Button DeleteEvent=new Button ("Delete Event Team");
            DeleteEvent.setOnAction(e -> {
                String name = tf1Id.getText();
                System.out.println(name);
                int id = 0;
                if(name.compareTo("") > 0) {
                    id = Integer.parseInt(name);
                }
                System.out.println(id);
                try {
                    stmt.executeUpdate("delete from team where tid = '" + id +"'");
                    System.out.println("Deleted");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            GridPane DeleteTeam=new GridPane();

            DeleteTeam.addRow(0, teamId,tf1Id);
            DeleteTeam.addRow(4, DeleteEvent);


            menu.setStyle("-fx-background-color: blue;");
            menu.setFillWidth(true);
            Button Titlebtn = new Button("Dashboard");
            Titlebtn.setPrefWidth(100);
            Titlebtn.setStyle("-fx-min-width: 200px; -fx-background-color: blue; -fx-text-fill: white; -fx-padding: 20px; -fx-font-size: 18px");
            Titlebtn.setOnAction(e -> {
                updateList(con);
                table.setItems(tableList);
                option.getChildren().clear();
                option.getChildren().add(table);
            });
            Button Adminbtn = new Button("Admin");
            Adminbtn.setPrefWidth(100);
            Adminbtn.setStyle("-fx-min-width: 200px; -fx-background-color: blue; -fx-text-fill: white; -fx-padding: 20px; -fx-font-size: 18px");
            Adminbtn.setOnAction(e -> {
                textOption = "Admin Login";
                option.getChildren().clear();
                option.getChildren().add(adminEvent);
                System.out.println(textOption);
            });
            Button Judgebtn = new Button("Judge");
            Judgebtn.setPrefWidth(100);
            Judgebtn.setStyle("-fx-min-width: 200px; -fx-background-color: blue; -fx-text-fill: white; -fx-padding: 20px; -fx-font-size: 18px");
            Judgebtn.setOnAction(e1 -> {
                textOption = "Judge Login";
                option.getChildren().clear();
                option.getChildren().add(judgeLogin);
                System.out.println(textOption);
            });

            Button DeleteTeambtn = new Button("Delete Team");
            DeleteTeambtn.setPrefWidth(100);
            DeleteTeambtn.setStyle("-fx-min-width: 200px; -fx-background-color: blue; -fx-text-fill: white; -fx-padding: 20px; -fx-font-size: 18px");
            DeleteTeambtn.setOnAction(e1 -> {
                textOption = "Delete Team";
                option.getChildren().clear();
                option.getChildren().add(DeleteTeam);
                System.out.println(textOption);
            });


            menu.getChildren().addAll(Titlebtn ,Adminbtn, Judgebtn, DeleteTeambtn);
            VBox.setVgrow(Adminbtn, Priority.ALWAYS);
            fileroot.getChildren().addAll(menu, option);

            StackPane root = new StackPane();
            root.getChildren().add(fileroot);

            Scene scene = new Scene(root,1000,750);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
