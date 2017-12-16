package cn.itcast.invoice.invoice.storeoper.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.invoice.storeoper.vo.StoreOperModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface StoreOperEbi extends BaseEbi<StoreOperModel> {
}
