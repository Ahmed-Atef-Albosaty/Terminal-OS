import java.util.*;

public class Parser {
    String commandName;
    String[] args;
    String outputFileName = null;
    boolean append = false;
    List<String[]> commands = new ArrayList<>();
    List<String> flags = new ArrayList<>();


    public boolean parse(String command) {
        if (command == null ||command.trim().isEmpty()) {
            return false;
        }
        commandName = null;
        args = new String[0];
        outputFileName = null;
        append = false;
        commands.clear();
        flags.clear();

        String[] actions = command.split("\\|");

        for (String s : actions) {
            String trimmed =s.trim();
            if (!trimmed.isEmpty()) {
                commands.add(trimmed.split("\\s+"));
            }
        }

        String main = command;

        if (command.contains(">>")) {
            int index = command.indexOf(">>");
            main = command.substring(0, index).trim();
            outputFileName = command.substring(index + 2).trim();
            append = true;
        } else if (command.contains(">")) {
            int index = command.indexOf(">");
            main = command.substring(0, index).trim();
            outputFileName = command.substring(index + 1).trim();
        }
        if (outputFileName != null) {
            if (outputFileName.startsWith("\"") && outputFileName.endsWith("\"")) {
                outputFileName = outputFileName.substring(1, outputFileName.length() - 1);
            }
            outputFileName = outputFileName.strip();
        }

        String[] tokens = main.trim().split("\\s+");
        if (tokens.length == 0) {
            return false;
        }
        commandName = tokens[0].trim();
        args = Arrays.copyOfRange(tokens, 1, tokens.length);
        List<String> argList= new ArrayList<>();

        for (String s : args) {
            if (s.startsWith("-")) {
                flags.add(s);
            }else{
                argList.add(s);
            }
        }

        args = argList.toArray(new String[0]);

        return true;
    }

    public String getCommandName() {
        return commandName;
    }
    public String getOutputFileName() {
        return outputFileName;
    }
    public List<String[]> getCommands() {
        return commands;
    }
    public boolean isAppend() {
        return append;
    }
    public String[] getArgs() {
        return args;
    }
    public boolean redirect(){
        return outputFileName != null;
    }
    public List<String> getFlags() {
        return flags;
    }

}
