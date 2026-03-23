package egovframework.com.pagination;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.Set;

public class EgovPaginationDialect extends AbstractDialect implements IExpressionObjectDialect {

    private final EgovKrdsPaginationRenderer egovKrdsPaginationRenderer;

    public EgovPaginationDialect(EgovKrdsPaginationRenderer egovKrdsPaginationRenderer) {
        super("EgovPaginationDialect");
        this.egovKrdsPaginationRenderer = egovKrdsPaginationRenderer;
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {
            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("egovKrdsPaginationRenderer");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return egovKrdsPaginationRenderer;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }

}
