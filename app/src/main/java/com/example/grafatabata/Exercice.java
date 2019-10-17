package com.example.grafatabata;

public class Exercice {
        private int prepareTime;
        private int workTime;
        private int restTime;
        private int longRestTime;
        private int cycleNb;
        private int tabataNb;

        public Exercice(int prepareTime, int workTime, int restTime, int longRestTime, int cycleNb, int tabataNb) {
            this.prepareTime = prepareTime;
            this.workTime = workTime;
            this.restTime = restTime;
            this.longRestTime = longRestTime;
            this.cycleNb = cycleNb;
            this.tabataNb = tabataNb;
        }

        public int getPrepareTime() {
            return prepareTime;
        }

        public void setPrepareTime(int prepareTime) {
            this.prepareTime = prepareTime;
        }

        public int getWorkTime() {
            return workTime;
        }

        public void setWorkTime(int workTime) {
            this.workTime = workTime;
        }

        public int getRestTime() {
            return restTime;
        }

        public void setRestTime(int restTime) {
            this.restTime = restTime;
        }

        public int getLongRestTime() {
            return longRestTime;
        }

        public void setLongRestTime(int longRestTime) {
            this.longRestTime = longRestTime;
        }

        public int getCycleNb() {
            return cycleNb;
        }

        public void setCycleNb(int cycleNb) {
            this.cycleNb = cycleNb;
        }

        public int getTabataNb() {
            return tabataNb;
        }

        public void setTabataNb(int tabataNb) {
            this.tabataNb = tabataNb;
        }
}
