package florian_haas.lucas.web.util;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import florian_haas.lucas.business.EntityBeanLocal;
import florian_haas.lucas.model.ReadOnlyEntity;

@Named
@RequestScoped
public class EntityBean {

	@EJB
	private EntityBeanLocal entityBean;

	public <T extends ReadOnlyEntity> Boolean exists(Long id, Class<T> entityClass) {
		return entityBean.exists(id, entityClass);
	}

}
