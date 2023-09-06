package tricolor.no1.util;

import tricolor.no1.model.spaces;

public class spacesUtils {

    /*
    *
    * */
    public static spaces NullSpaces(spaces s)
    {
        if (s.bili=="" || s.bili == " ")
        {
            s.bili=null;
        }
        if (s.csdn=="" || s.csdn == " ")
        {
            s.csdn = null;
        }
        if (s.github=="" || s.github ==" ")
        {
            s.github = null;
        }
        if (s.juejin == "" || s.juejin == " ")
        {
            s.juejin = null;
        }

        return s;
    }
}
