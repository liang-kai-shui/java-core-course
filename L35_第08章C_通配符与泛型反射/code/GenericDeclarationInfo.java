import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;
public class GenericDeclarationInfo {
    public static void main(String[] args) throws ReflectiveOperationException {
        TypeVariable<?> variable = DeclaredTypes.class.getTypeParameters()[0];
        System.out.println(variable.getBounds()[0].getTypeName());
        ParameterizedType words = (ParameterizedType) DeclaredTypes.class.getDeclaredField("words").getGenericType();
        System.out.println(words.getActualTypeArguments()[0].getTypeName());
        ParameterizedType unknown = (ParameterizedType) DeclaredTypes.class.getDeclaredField("unknown").getGenericType();
        WildcardType wildcard = (WildcardType) unknown.getActualTypeArguments()[0];
        System.out.println(wildcard.getUpperBounds()[0].getTypeName());
        System.out.println(wildcard.getLowerBounds().length);
    }
}
class DeclaredTypes<T> { List<String> words; List<?> unknown; }
