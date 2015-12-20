import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

/**
 * Created by fvdma on 17/12/2015.
 */
@WebServlet("/demo/specificTransaction")
public class TransactionServletExample extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        CacheFactory.ensureCluster();
        NamedCache cache = CacheFactory.getCache("transactions");

        Integer iSessao = (Integer) request.getSession().getAttribute("valorSessao");
        if(iSessao == null)
        {
        	request.getSession().setAttribute("valorSessao", 0);
        	iSessao = 0;
        }
        else
        {
        	iSessao++;
        	request.getSession().setAttribute("valorSessao", iSessao);
        }
        
        int transId = Integer.parseInt(request.getParameter("transId"));
        Object o = cache.get(transId);
        Integer i = 0;
        if(o == null)
        {
            cache.put(transId, i);            
        }
        else
        {
            i = (Integer) o;
            i++;
            cache.put(transId, i);        
        }

        response.getWriter().println("<COHEREHCE_CACHE>" + i + "</COHEREHCE_CACHE>");
        response.getWriter().println("<VALOR_SESSAO>" + iSessao + "</VALOR_SESSAO>");

    }
}
