package lightfilesystem;

public class AccessChecker {
    public static Access getUserAccess(File file, User user){
        if(file.getAuthor().equals(user)){
            return Access.ALL;
        }
        if(file.getGroups().contains(user.getGroup())){
            return file.getGroupsAccess();
        }
        return file.getOtherAccess();
    }
}
