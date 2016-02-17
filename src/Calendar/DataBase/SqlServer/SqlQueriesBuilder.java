package Calendar.DataBase.SqlServer;

public class SqlQueriesBuilder {


    public SqlQueriesBuilder() {
    }

    public String writeQuery(String s) {
        String[] codedQuery = s.split("/");
        String finalQuery = getQueryType(codedQuery, codedQuery[0]);
        return finalQuery;
    }

    private String getQueryType(String[] s, String type) {
        String ret;
        switch (type){
            case "GET":
                ret = "SELECT ";
                ret = buildSelect(s, ret);
                break;
            case "POST":
                ret = "INSERT INTO ";
                ret = buildInsert(s, ret);
                break;
            case "UPDATE":
                ret = "UPDATE ";
                break;
            default:
                throw new UnsupportedQueryException();
        }
        return ret;
    }

    private String buildInsert(String[] s, String ret) {

        return ret;
    }

    private String buildSelect(String[] s, String ret) {
        if(s.length <= 3){
            if(s.length == 3){
                ret += setColumns(s[s.length - 2]);
                ret += buildPath(s[s.length - 2]);
                return ret+= buildParameters(s[s.length-1]);
            }
            ret += setColumns(s[s.length-1]);
            return ret += buildPath(s[s.length-1]);
        }
        return ret;
    }

    private String buildParameters(String s) {
        String ret = "WHERE ";
        String[] array = s.split("&");
        for (int i = 0; i < array.length; i++) {
            ret += array[i];
            if(i < array.length-1)
                ret+=" AND ";
        }
        return ret;
    }

    private String buildPath(String s) {
        String ret = "FROM ";
        String[] array = s.split("&");
        return ret += array[0]+" ";
    }

    private String setColumns(String s) {
        String[] array = s.split("&");
        if(array.length == 1) return "* ";
        String ret = "";
        for (int i = 1; i < array.length; i++) {
            ret += array[i]+" ";
            if(i < array.length-1)
                ret+=",";
        }
        return ret;
    }

    private class UnsupportedQueryException extends RuntimeException {
    }
}
