package lightfilesystem;

import lombok.Data;

@Data
public class AccessChecker {
    File file;
    Access groupsAccess;
    Access otherAccess;

    AccessChecker(File file){
        this.file = file;
    }

    public Access getUserAccess(User user){
        if(file.author.equals(user)){
            return Access.ALL;
        }
        return Access.NONE;
    }

    public static AccessChecker initAccessChecker(File file){
        return new AccessChecker(file);
    }
}
