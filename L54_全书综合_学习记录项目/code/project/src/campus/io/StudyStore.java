package campus.io;
import java.io.*;
import campus.model.StudyBook;
import campus.model.StudyEntry;
public final class StudyStore {
    private StudyStore() { }
    public static StudyBook read(Reader source) throws IOException {
        BufferedReader reader = source instanceof BufferedReader ? (BufferedReader) source : new BufferedReader(source);
        StudyBook draft = new StudyBook(); String line; int number = 0;
        while ((line = reader.readLine()) != null) {
            number++;
            try {
                String[] fields = line.split("\t", -1); if (fields.length != 4) throw new IllegalArgumentException("need four fields");
                int id = Integer.parseInt(fields[0]), minutes = Integer.parseInt(fields[1]);
                if (!fields[2].equals("true") && !fields[2].equals("false")) throw new IllegalArgumentException("invalid boolean");
                draft.add(new StudyEntry(id, fields[3], minutes, fields[2].equals("true")));
            } catch (IllegalArgumentException e) { throw new IOException("invalid line " + number, e); }
        }
        return draft;
    }
    public static void write(StudyBook book, Writer target) throws IOException {
        for (StudyEntry e : book.snapshot()) {
            target.write(e.getId() + "\t" + e.getMinutes() + "\t" + e.isCompleted() + "\t" + e.getTitle() + "\n");
        }
        target.flush();
    }
}
