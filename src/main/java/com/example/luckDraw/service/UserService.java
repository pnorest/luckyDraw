package com.example.luckDraw.service;

import com.example.luckDraw.mapper.UserMapper;
import com.example.luckDraw.model.Result;
import com.example.luckDraw.model.TkUser;
import com.example.luckDraw.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * @ClassName ActivityService
 * @Description TODO
 * @Author Pnorest
 * @Date 2020/1/6 10:27
 * @Version 1.0
 **/
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;



    @Value("${tmpPath}")
    private String tmpPath;

    public List<TkUser> findUser() {
        return userMapper.findUser();
    }

    public void addUser(TkUser tkUser) {
        userMapper.addUser(tkUser);
    }

    public void updateUser(TkUser tkUser) {
        userMapper.updateUser(tkUser);
    }







    public Result saveExcelInfo(String fileUrl, TkUser tkUser) throws Exception {

        //写入数据之前需要清掉之前的所有数据（把这个用户表中的所有数据的status都置为1）
        userMapper.setAllUserStatus();


        //开始插入数据
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        //String filePath = "D:\\MyDocuments\\itw_zhouliang01\\桌面\\电核标准化手册.xlsx";
        String filePath = fileUrl;

        //int combineCellNum=0;//第几个合并单元格     0表示第一个

        logger.info("引用Excel地址为：" + filePath);
        String columns[] = {"姓名", "手机号","对应分组id"};//关键词  回复
        wb = readExcel(filePath);
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<Map<String, String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //List<CellRangeAddress> listCombineCell = getCombineCell(sheet);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            if (rownum < 2) {
                return new Result(Result.CODE.SUCCESS.getCode(), "上传的是空模板，已清除原数据");
            }
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();

            for (int i = 1; i < rownum; i++) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }

                } else {
                    break;//中断循环体   return表示结束被调函数这次调用并返回值
                }


                list.add(map);
                //list去重  去重生效
                removeDuplicate(list);
            }
        }
        //遍历解析出来的list
        //把excel解析出来的内容放入新的list容器中
        List<TkUser> tkUsers = new ArrayList<>();
        if (list == null) {
            return new Result(Result.CODE.FAIL.getCode(), "列表数据为空");
        }
        for (Map<String, String> map : list) {

            TkUser user = new TkUser();
            String username = map.get("姓名");
            String phone = map.get("手机号");
            String groupId = map.get("对应分组id");

            logger.info("用户名:" + username);
            logger.info("手机号" + phone);
            logger.info("对应分组id" + groupId);
            if ("".equals(username) || "".equals(phone)|| "".equals(groupId)) {
                continue;//不插入一行中有空关键字或者回复的数据
            }
            user.setName(username);
            user.setPhone(phone);
            user.setGroupId(groupId);
            user.setActivityId(tkUser.getActivityId());

            tkUsers.add(user);//增加到列表中
        }
        userMapper.insertUserInfo(tkUsers);//批量插入excel中数据   批量插入无法获取到自增id（目前无好办法），故插入，查询，再插入这样

        List<TkUser> tkUserList=userMapper.findUser();
        return new Result(Result.CODE.SUCCESS.getCode(), "批量插入Excel机器人回复数据成功", tkUserList);
    }


    //读取excel
    public Workbook readExcel(String filePath) {
        Workbook wb =null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }
        } catch (FileNotFoundException e) {
            logger.error("e",e);
        } catch (IOException e) {
            logger.error("e",e);
        } finally {
            try {
                if(is!=null)
                {
                    is.close();
                }
                logger.info("finally");
            } catch (IOException e) {
                logger.error("e",e);
            }
        }
        return wb;
    }

    public Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }


    public void downloadTemplate(HttpServletResponse response, HttpServletRequest request, String templeteName) throws IOException {
        OutputStream outp = null;
        FileInputStream in = null;
        try {
            String fileName = templeteName; //要下载的模板文件
//            if(templeteName!=null){
//                if(!templeteName.endsWith(".xls")){
//                    fileName = templeteName + ".xls";
//                }
//            }
            //String ctxPath = request.getSession().getServletContext().getRealPath(File.separator) + File.separator + "template" + File.separator;
            String filedownload = tmpPath + "/robot" + "/" + fileName;
            fileName = URLEncoder.encode(fileName, "UTF-8");
            // 要下载的模板所在的绝对路径
            response.reset();
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType("application/octet-stream;charset=UTF-8");
            outp = response.getOutputStream();
            in = new FileInputStream(filedownload);
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) > 0) {
                outp.write(b, 0, i);
            }
            outp.flush();
        } catch (Exception e) {
            logger.error("e",e);

        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
            if (outp != null) {
                outp.close();
                outp = null;
            }
        }


    }



    public Result uploadFile(MultipartFile file) throws IOException {
        //前端没有选择文件，file文件为空
        if (file.isEmpty()) {
            return new Result(Result.CODE.SUCCESS.getCode(), "请选择一个文件");
        }
        String localFileName = file.getOriginalFilename();//文件名字
        logger.info("上传文件名为：" + localFileName);
        String fileType = StringUtils.substringAfterLast(localFileName, ".");//文件类型
        logger.info("上传文件类型为：" + fileType);
        //String fileName= UUID.randomUUID().toString().replaceAll("-","")+"."+fileType;
        //String fileUrl="D:\\MyDocuments\\itw_zhouliang01\\桌面\\"+fileName;
        //Tue Jan 07 00:00:00 CST 2020
        String fileUrl = tmpPath + "/robot" + "/" + DateUtils.convertDateToString(DateUtils.getCurrentDateTime()) + "/" + localFileName;
        File resultFile = new File(fileUrl);
        if (!resultFile.getParentFile().exists()) {
            resultFile.getParentFile().mkdirs();
        }
        Files.copy(file.getInputStream(), resultFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        //把上传的文件copy到服务器的某个位置（fileUrl）
        //file1.getAbsolutePath();
        logger.info("把上传的文件copy到项目的某个位置:" + resultFile.getAbsolutePath());
        return new Result(Result.CODE.SUCCESS.getCode(), "上传成功", resultFile.getAbsolutePath());
    }


////    robot合并单元格处理
//
//    /**
//     * 合并单元格处理,获取合并行
//     *
//     * @param sheet
//     * @return List<CellRangeAddress>
//     */
//    private List<CellRangeAddress> getCombineCell(Sheet sheet) {
//        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
//        // 获得一个 sheet 中合并单元格的数量
//        int sheetmergerCount = sheet.getNumMergedRegions();
//        // 遍历合并单元格
//        for (int i = 0; i < sheetmergerCount; i++) {
//            // 获得合并单元格加入list中
//            CellRangeAddress ca = sheet.getMergedRegion(i);
//            list.add(ca);
//        }
//        return list;
//    }

//    /**
//     * 判断单元格是否为合并单元格，是的话则将单元格的值返回
//     *
//     * @param listCombineCell 存放合并单元格的list
//     * @param cell            需要判断的单元格
//     * @param sheet           sheet
//     * @return
//     */
//    private boolean isCombineCell(List<CellRangeAddress> listCombineCell,
//                                  Cell cell, Sheet sheet) throws Exception {
//        int firstC = 0;
//        int lastC = 0;
//        int firstR = 0;
//        int lastR = 0;
//        for (CellRangeAddress ca : listCombineCell) {
//            // 获得合并单元格的起始行, 结束行, 起始列, 结束列
//            firstC = ca.getFirstColumn();
//            lastC = ca.getLastColumn();
//            firstR = ca.getFirstRow();
//            lastR = ca.getLastRow();
//            if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
//                if (cell.getColumnIndex() >= firstC
//                        && cell.getColumnIndex() <= lastC) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


    //list去重（效率高点的） 利用LinkedHashSet不能添加重复数据并能保证添加顺序性的特性
    private void removeDuplicate(List<Map<String, String>> list) {
        LinkedHashSet<Map<String, String>> set = new LinkedHashSet<Map<String, String>>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }


}
