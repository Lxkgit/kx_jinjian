package com.kx.system.domain.sync;

import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Data;

import java.util.Date;

@Data
public class SysTeamMemberVo {
    //主键
    @CsvBindByPosition(position = 0)
    private Long id;
    //加入日期
    @CsvCustomBindByPosition(position = 1, converter = CsvJavaUtilDateConvert.class)
    private Date joinDate;
    //成员号
    @CsvBindByPosition(position = 2)
    private String userId;
    //所属营销团队ID
    @CsvBindByPosition(position = 3)
    //BIGINT not null,
    private Long mktTeamId;
    //是否团队负责人ID
    @CsvBindByPosition(position = 4)
    private String isLeader;
    //创建人ID
    @CsvBindByPosition(position = 5)
    private String createUserId;
    //最近一次修改日期
    @CsvBindByPosition(position = 6)
    private String lastModifyDate;
    //最近一次修改人
    @CsvBindByPosition(position = 7)
    private String lastModifyUser;
    //岗位
    @CsvBindByPosition(position = 8)
    private String position;
    //入行日期
    @CsvBindByPosition(position = 9)
    private String enterdate;
}
