package com.graduation.compusinfo.display.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.List;

/**
 * @author zzk
 * @date 2020/3/4 0:12
 */
@Data
public class CommentDTO {

    //总回复数
    public int replytotal;

    @JsonBackReference
    public List<CommentDTO> child;

    public int length;



    public String img;
}
