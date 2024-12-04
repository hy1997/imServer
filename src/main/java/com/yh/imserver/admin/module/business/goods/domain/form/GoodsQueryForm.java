package com.yh.imserver.admin.module.business.goods.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yh.imserver.admin.module.business.goods.constant.GoodsStatusEnum;
import com.yh.imserver.base.common.domain.PageParam;
import com.yh.imserver.base.common.json.deserializer.DictValueVoDeserializer;
import com.yh.imserver.base.common.swagger.SchemaEnum;
import com.yh.imserver.base.common.validator.enumeration.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商品 分页查询
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-10-25 20:26:54
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class GoodsQueryForm extends PageParam {

    @Schema(description = "商品分类")
    private Integer categoryId;

    @Schema(description = "搜索词")
    private String searchWord;

    @SchemaEnum(GoodsStatusEnum.class)
    @CheckEnum(message = "商品状态错误", value = GoodsStatusEnum.class, required = false)
    private Integer goodsStatus;

    @Schema(description = "产地")
    @JsonDeserialize(using = DictValueVoDeserializer.class)
    private String place;

    @Schema(description = "上架状态")
    private Boolean shelvesFlag;

    @Schema(hidden = true)
    private Boolean deletedFlag;
}
