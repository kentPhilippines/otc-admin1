package com.ruoyi.web.controller.sms;


import com.google.common.base.Preconditions;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import com.ruoyi.common.core.domain.vo.BatchUpdateSmsVO;
import com.ruoyi.common.core.domain.vo.SmsTaskTblPreSummaryVO;
import com.ruoyi.common.core.domain.vo.SmsTaskTblSummaryVO;
import com.ruoyi.common.core.domain.vo.SmsTaskTblVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISmsTaskTblService;
import com.ruoyi.web.controller.sms.converter.SmsTaskTblConverter;
import com.ruoyi.web.controller.sms.converter.SmsTaskTblVOConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * WS短信任务配置Controller
 *
 * @author dorion
 * @date 2024-06-01
 */
@Controller
@RequestMapping("/sms/task/ws")
public class WSSmsTaskTblController extends BaseController {
    private String prefix = "sms/task/ws";

    @Autowired
    private ISmsTaskTblService smsTaskTblService;

    @Autowired
    private ServerConfig serverConfig;

    @RequiresPermissions("sms:task:ws:view")
    @GetMapping()
    public String task() {
        return prefix + "/task";
    }

    @RequiresPermissions("sms:task:ws:view")
    @GetMapping("/summary")
    public String taskSummary() {
        return prefix + "/taskSummary";
    }

    /**
     * 查询WS短信任务配置列表
     */
    @RequiresPermissions("sms:task:ws:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SmsTaskTbl smsTaskTbl) {
        startPage();
        List<SmsTaskTbl> list = smsTaskTblService.selectSmsTaskTblList(smsTaskTbl);
        return getDataTable(list);
    }

    /**
     * 导出WS短信任务配置列表
     */
    @RequiresPermissions("sms:task:ws:export")
    @Log(title = "WS短信任务配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SmsTaskTbl smsTaskTbl) {
        List<SmsTaskTbl> list = smsTaskTblService.selectSmsTaskTblList(smsTaskTbl);
        List<SmsTaskTblPreSummaryVO> smsTaskTblPreSummaryVOList = SmsTaskTblConverter.INSTANCE.to(list);
        ExcelUtil<SmsTaskTblPreSummaryVO> util = new ExcelUtil<SmsTaskTblPreSummaryVO>(SmsTaskTblPreSummaryVO.class);
        return util.exportExcel(smsTaskTblPreSummaryVOList, "WS短信任务配置数据");
    }

    /**
     * 新增WS短信任务配置
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增WS短信任务配置
     */
    @GetMapping("/addBatch")
    public String addBatch() {
        return prefix + "/addBatch";
    }

    @GetMapping("/update/{ids}")
    public String update(@PathVariable("ids") String ids, ModelMap mmap) {
        mmap.put("ids", ids);
        return prefix + "/update";
    }

    @PostMapping("/update")
    @RequiresPermissions("sms:task:ws:edit")
    @Log(title = "WS短信任务配置", businessType = BusinessType.UPDATE)
    @ResponseBody
    public AjaxResult update(BatchUpdateSmsVO batchUpdateSmsVO) {
        smsTaskTblService.updateBatchUpdateSmsVO(batchUpdateSmsVO);
        return toAjax(1);
    }

    /**
     * 新增保存WS短信任务配置
     */
    @RequiresPermissions("sms:task:ws:add")
    @Log(title = "WS短信任务配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult addSave(@RequestParam("file") MultipartFile file, SmsTaskTbl smsTaskTbl) throws IOException {

        String taskName = smsTaskTbl.getTaskName();
        Preconditions.checkState(taskName.matches("^[a-zA-Z0-9_]+$"), "任务名称只能是数字,字母和下划线");

        String originalFilename = file.getOriginalFilename();
        checkFileExtendName(originalFilename);

        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 上传并返回新文件名称
        String fileName = FileUploadUtils.upload(filePath, file);
        String url = serverConfig.getUrl() + fileName;

        try (InputStream inputStream = file.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            MessageDigest md = MessageDigest.getInstance("MD5");
            long lineCount = 0;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            String line;
            String phoneNumber = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (StringUtils.isEmpty(phoneNumber)) {
                    phoneNumber = line;
                }
                lineCount++;
                byte[] lineBytes = line.getBytes();
                byteArrayOutputStream.write(lineBytes);
                byteArrayOutputStream.write('\n');  // 添加换行符
                md.update(lineBytes);
            }

            byte[] digest = md.digest();
            String md5 = bytesToHex(digest);
//            String base64 = Base64.getEncoder().encodeToString(smsTaskTbl.getContext().getBytes());

            // 设置SmsTaskTbl对象的属性
            smsTaskTbl.setSmsBusiType("WS");
            smsTaskTbl.setFileMd5(md5);
//            smsTaskTbl.setContext(base64);
            smsTaskTbl.setFilePath(url);
            smsTaskTbl.setFileName(originalFilename);
            smsTaskTbl.setTotalCount(lineCount);
            smsTaskTbl.setPhoneNumber(phoneNumber);
            return toAjax(smsTaskTblService.insertSmsTaskTbl(smsTaskTbl));
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to process file", e);
        }


    }

    private static void checkFileExtendName(String originalFilename) {
        String[] split = originalFilename.split(".");
        Preconditions.checkState("txt".equals(split[split.length - 1]), "文件格式错误,只支持txt文件上传");
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 新增保存WS短信任务配置
     */
    @RequiresPermissions("sms:task:ws:add")
    @Log(title = "WS短信任务批量配置", businessType = BusinessType.INSERT)
    @PostMapping("/addBatch")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult addBatchSave(@RequestParam("file") MultipartFile file, SmsTaskTblVO smsTaskTblVO) throws IOException, InvalidExtensionException, NoSuchAlgorithmException {
        int splitNumber = 0;
        splitNumber = smsTaskTblVO.getSplitNumber();
        String[] allowedExtension = new String[]{"txt"};
//        String baseDir = "/your/upload/directory"; // 上传文件的基础目录
        String filePath = RuoYiConfig.getUploadPath();
        // 拆分文件并上传
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String placeHolder = smsTaskTblVO.getPlaceHolder();
        String context = smsTaskTblVO.getContext();
        Preconditions.checkState(context.indexOf(placeHolder) != -1, "文本内容中不存在占位符");

        String placeHolderFill = smsTaskTblVO.getPlaceHolderFill();
        Preconditions.checkState(StringUtils.isNotEmpty(placeHolderFill), "占位符填充内容不能为空");
        String[] split = placeHolderFill.split("\r\n");

        Preconditions.checkState(splitNumber > 0, "物料切割数量必须大于0");
        Preconditions.checkState(split.length == splitNumber, "占位填充符数量与物料切割数量不一致");

        List<String> buffer = new ArrayList<>();
        String line;
        String phoneNumber = null;
        long lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            buffer.add(line);
            if ( StringUtils.isEmpty(phoneNumber)) {
                phoneNumber = line;
            }

            lineNumber++;
        }
        smsTaskTblVO.setLineCount(lineNumber);
        reader.close();
        smsTaskTblVO.setPhoneNumber(phoneNumber);
        String originalFilename = file.getOriginalFilename();
        int totalLines = buffer.size();
        int linesPerFile = totalLines / splitNumber;
        List<SmsTaskTbl> smsTaskTblList = new ArrayList<>();
        for (int i = 0; i < splitNumber; i++) {
            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);
            String outputFileName = originalFilename + "_" + (i + 1) + ".txt";

            int startLine = i * linesPerFile;
//            int endLine = Math.min(startLine + linesPerFile, totalLines);
            int endLine = (i == splitNumber - 1) ? totalLines : startLine + linesPerFile;

            MessageDigest md = MessageDigest.getInstance("MD5");
            long lineCount = 0;

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(pos, StandardCharsets.UTF_8))) {
                for (int j = startLine; j < endLine; j++) {
                    String writeLine = buffer.get(j);
                    writer.write(writeLine);
                    writer.newLine();
                    md.update(writeLine.getBytes());
                    lineCount++;
                }
                writer.flush();
            } catch (IOException e) {
                logger.error("Failed to write file", e);
            } finally {
                try {
                    pos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 调用上传方法
            String fileName = FileUploadUtils.upload(filePath, outputFileName, pis, allowedExtension);
            pis.close();
            byte[] digest = md.digest();
            String md5 = bytesToHex(digest);
            SmsTaskTbl smsTaskTbl = SmsTaskTblVOConverter.INSTANCE.to(smsTaskTblVO);
            smsTaskTbl.setFileName(outputFileName);
            smsTaskTbl.setTaskName(smsTaskTblVO.getTaskName() + "_" + (i + 1) + "_" + placeHolder);
            smsTaskTbl.setContext(smsTaskTblVO.getContext().replaceAll(placeHolder, split[i]));
            smsTaskTbl.setFilePath(serverConfig.getUrl() + fileName);
            smsTaskTbl.setFileMd5(md5);
            smsTaskTbl.setSmsBusiType("WS");
            smsTaskTbl.setTotalCount(lineCount);
            smsTaskTblList.add(smsTaskTbl);

        }
        smsTaskTblVO.setSmsTaskTblList(smsTaskTblList);
        smsTaskTblService.insertSmsTaskTblBatch(smsTaskTblVO);
        return toAjax(split.length);
    }

    private int countLines(BufferedReader reader) throws IOException {
        int lines = 0;
        while (reader.readLine() != null) {
            lines++;
        }
        return lines;
    }


    /**
     * 修改WS短信任务配置
     */
    @RequiresPermissions("sms:task:ws:edit")
    @GetMapping("/edit/{idSmsTask}")
    public String edit(@PathVariable("idSmsTask") Long idSmsTask, ModelMap mmap) {
        SmsTaskTbl smsTaskTbl = smsTaskTblService.selectSmsTaskTblByIdSmsTask(idSmsTask);
        mmap.put("smsTaskTbl", smsTaskTbl);
        return prefix + "/edit";
    }


    @RequiresPermissions("sms:task:ws:export")
    @GetMapping("/exportTaskDetail/{idSmsTask}")
    public ResponseEntity<byte[]> exportTaskDetail(@PathVariable("idSmsTask") Long idSmsTask) {
        byte[] report = smsTaskTblService.getReport(idSmsTask);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "sample.xlsx");

        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }

    @RequiresPermissions("sms:task:ws:export")
    @PostMapping("/exportSummaryTaskUrl")
    @ResponseBody
    public AjaxResult exportSummaryTaskUrl(SmsTaskTbl smsTaskTbl) {
        List<SmsTaskTbl> list = smsTaskTblService.selectSmsTaskTblList(smsTaskTbl);
        List<SmsTaskTblSummaryVO> smsTaskTblPreSummaryVOList = SmsTaskTblConverter.INSTANCE.toSmsTaskTblSummaryVOList(list);
        ExcelUtil<SmsTaskTblSummaryVO> util = new ExcelUtil<SmsTaskTblSummaryVO>(SmsTaskTblSummaryVO.class);
        return util.exportExcel(smsTaskTblPreSummaryVOList, "WS短信任务配置数据");
    }

    /**
     * 修改保存WS短信任务配置
     */
    @RequiresPermissions("sms:task:ws:edit")
    @Log(title = "WS短信任务配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult editSave(SmsTaskTbl smsTaskTbl) {
        return toAjax(smsTaskTblService.updateSmsTaskTbl(smsTaskTbl));
    }

    /**
     * 删除WS短信任务配置
     */
    @RequiresPermissions("sms:task:ws:remove")
    @Log(title = "WS短信任务配置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(smsTaskTblService.deleteSmsTaskTblByIdSmsTasks(ids));
    }


    @RequiresPermissions("sms:task:ws:edit")
    @Log(title = "租户", businessType = BusinessType.ACTIVE_DATA)
    @PostMapping("/completeTask")
    @ResponseBody
    public AjaxResult completeTask(String ids) throws Exception {
        return toAjax(smsTaskTblService.complete(ids));
    }
}
