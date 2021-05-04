package lightfilesystem;

import lombok.Data;

@Data
public class AccessChecker {
    File file;
    Access groupsAccess = Access.NONE;
    Access otherAccess = Access.NONE;

    AccessChecker(File file){
        this.file = file;
    }

    public Access getUserAccess(User user){
        if(file.author.equals(user)){
            return Access.ALL;
        }

        if(this.file.groups.contains(user.getGroup())){
            return this.groupsAccess;
        }

        return this.otherAccess;
    }

    public static AccessChecker initAccessChecker(File file){
        return new AccessChecker(file);
    }
}
