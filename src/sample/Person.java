package sample;

public class Person {
        int id, member, batch, judge1, judge2, judge3;
        String nameTeam, perfType;

        Person(int id, int member, int batch, int judge1, int judge2, int judge3, String nameTeam, String perfType){
            this.id = id;
            this.batch = batch;
            this.member = member;
            this.judge1 = judge1;
            this.judge2 = judge2;
            this.judge3 = judge3;
            this.nameTeam = nameTeam;
            this.perfType = perfType;
        }

        public int getId(){
            return id;
        }

        public int getBatch() {
            return batch;
        }

        public int getJudge1() {
            return judge1;
        }

        public int getMember() {
            return member;
        }

        public int getJudge2() {
            return judge2;
        }

        public int getJudge3() {
            return judge3;
        }

        public String getNameTeam() {
            return nameTeam;
        }

        public String getPerfType() {
            return perfType;
        }

}
