package cn.itcast.invoice.invoice.storedetail.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.invoice.storedetail.vo.StoreDetailModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface StoreDetailEbi extends BaseEbi<StoreDetailModel> {
}
