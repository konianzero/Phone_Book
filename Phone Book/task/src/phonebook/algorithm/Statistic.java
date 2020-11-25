package phonebook.algorithm;

import static java.lang.System.currentTimeMillis;

abstract class Statistic {
    protected long entriesFound = 0;
    protected long allEntries = 0;
    protected long startTime;
    protected long timeTaken;

    public void startTime() {
        startTime = currentTimeMillis();
    }

    public void stopTime() {
        timeTaken = currentTimeMillis() - startTime;
    }

    public String getStatistic() {
        return String.format("Found %d / %d entries. %s",
                entriesFound,
                allEntries,
                String.format("%1$TM min. %1$TS sec. %1$TL ms.", timeTaken));
    }
}
