/*
 * Camel EndpointConfiguration generated by camel-api-component-maven-plugin
 */
package org.apache.camel.component.google.sheets;

import org.apache.camel.spi.ApiMethod;
import org.apache.camel.spi.ApiParam;
import org.apache.camel.spi.ApiParams;
import org.apache.camel.spi.Configurer;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

/**
 * Camel endpoint configuration for {@link com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values}.
 */
@ApiParams(apiName = "data", 
           description = "The values collection of methods",
           apiMethods = {@ApiMethod(methodName = "append", description="Appends values to a spreadsheet", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$Append append(String spreadsheetId, String range, com.google.api.services.sheets.v4.model.ValueRange content)"}), @ApiMethod(methodName = "batchClear", description="Clears one or more ranges of values from a spreadsheet", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$BatchClear batchClear(String spreadsheetId, com.google.api.services.sheets.v4.model.BatchClearValuesRequest content)"}), @ApiMethod(methodName = "batchClearByDataFilter", description="Clears one or more ranges of values from a spreadsheet", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$BatchClearByDataFilter batchClearByDataFilter(String spreadsheetId, com.google.api.services.sheets.v4.model.BatchClearValuesByDataFilterRequest content)"}), @ApiMethod(methodName = "batchGet", description="Returns one or more ranges of values from a spreadsheet", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$BatchGet batchGet(String spreadsheetId)"}), @ApiMethod(methodName = "batchGetByDataFilter", description="Returns one or more ranges of values that match the specified data filters", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$BatchGetByDataFilter batchGetByDataFilter(String spreadsheetId, com.google.api.services.sheets.v4.model.BatchGetValuesByDataFilterRequest content)"}), @ApiMethod(methodName = "batchUpdate", description="Sets values in one or more ranges of a spreadsheet", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$BatchUpdate batchUpdate(String spreadsheetId, com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest content)"}), @ApiMethod(methodName = "batchUpdateByDataFilter", description="Sets values in one or more ranges of a spreadsheet", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$BatchUpdateByDataFilter batchUpdateByDataFilter(String spreadsheetId, com.google.api.services.sheets.v4.model.BatchUpdateValuesByDataFilterRequest content)"}), @ApiMethod(methodName = "clear", description="Clears values from a spreadsheet", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$Clear clear(String spreadsheetId, String range, com.google.api.services.sheets.v4.model.ClearValuesRequest content)"}), @ApiMethod(methodName = "get", description="Returns a range of values from a spreadsheet", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$Get get(String spreadsheetId, String range)"}), @ApiMethod(methodName = "update", description="Sets values in a range of a spreadsheet", signatures={"com.google.api.services.sheets.v4.Sheets$Spreadsheets$Values$Update update(String spreadsheetId, String range, com.google.api.services.sheets.v4.model.ValueRange content)"})}, aliases = {})
@UriParams
@Configurer(extended = true)
public final class SheetsSpreadsheetsValuesEndpointConfiguration extends GoogleSheetsConfiguration {
    @UriParam
    @ApiParam(optional = false, apiMethods = {@ApiMethod(methodName = "batchClear", description="The com.google.api.services.sheets.v4.model.BatchClearValuesRequest")})
    private com.google.api.services.sheets.v4.model.BatchClearValuesRequest batchClearValuesRequest;
    @UriParam
    @ApiParam(optional = false, apiMethods = {@ApiMethod(methodName = "batchGetByDataFilter", description="The com.google.api.services.sheets.v4.model.BatchGetValuesByDataFilterRequest")})
    private com.google.api.services.sheets.v4.model.BatchGetValuesByDataFilterRequest batchGetValuesByDataFilterRequest;
    @UriParam
    @ApiParam(optional = false, apiMethods = {@ApiMethod(methodName = "batchUpdateByDataFilter", description="The com.google.api.services.sheets.v4.model.BatchUpdateValuesByDataFilterRequest")})
    private com.google.api.services.sheets.v4.model.BatchUpdateValuesByDataFilterRequest batchUpdateValuesByDataFilterRequest;
    @UriParam
    @ApiParam(optional = false, apiMethods = {@ApiMethod(methodName = "batchUpdate", description="The com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest")})
    private com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest batchUpdateValuesRequest;
    @UriParam
    @ApiParam(optional = false, apiMethods = {@ApiMethod(methodName = "clear", description="The com.google.api.services.sheets.v4.model.ClearValuesRequest")})
    private com.google.api.services.sheets.v4.model.ClearValuesRequest clearValuesRequest;
    @UriParam
    @ApiParam(optional = false, apiMethods = {@ApiMethod(methodName = "batchClearByDataFilter", description="The com.google.api.services.sheets.v4.model.BatchClearValuesByDataFilterRequest")})
    private com.google.api.services.sheets.v4.model.BatchClearValuesByDataFilterRequest content;
    @UriParam
    @ApiParam(optional = false, apiMethods = {@ApiMethod(methodName = "append", description="The A1 notation(https://developers.google.com/workspace/sheets/api/guides/concepts#cell) of a range to search for a logical table of data. Values are appended after the last row of the table."), @ApiMethod(methodName = "clear", description="The A1 notation or R1C1 notation(https://developers.google.com/workspace/sheets/api/guides/concepts#cell) of the values to clear."), @ApiMethod(methodName = "get", description="The A1 notation or R1C1 notation(https://developers.google.com/workspace/sheets/api/guides/concepts#cell) of the range to retrieve values from."), @ApiMethod(methodName = "update", description="The A1 notation(https://developers.google.com/workspace/sheets/api/guides/concepts#cell) of the values to update.")})
    private String range;
    @UriParam
    @ApiParam(optional = false, apiMethods = {@ApiMethod(methodName = "append", description="The ID of the spreadsheet to update"), @ApiMethod(methodName = "batchClear", description="The ID of the spreadsheet to update"), @ApiMethod(methodName = "batchClearByDataFilter", description="The ID of the spreadsheet to update"), @ApiMethod(methodName = "batchGet", description="The ID of the spreadsheet to retrieve data from"), @ApiMethod(methodName = "batchGetByDataFilter", description="The ID of the spreadsheet to retrieve data from"), @ApiMethod(methodName = "batchUpdate", description="The ID of the spreadsheet to update"), @ApiMethod(methodName = "batchUpdateByDataFilter", description="The ID of the spreadsheet to update"), @ApiMethod(methodName = "clear", description="The ID of the spreadsheet to update"), @ApiMethod(methodName = "get", description="The ID of the spreadsheet to retrieve data from"), @ApiMethod(methodName = "update", description="The ID of the spreadsheet to update")})
    private String spreadsheetId;
    @UriParam
    @ApiParam(optional = false, apiMethods = {@ApiMethod(methodName = "append", description="The com.google.api.services.sheets.v4.model.ValueRange"), @ApiMethod(methodName = "update", description="The com.google.api.services.sheets.v4.model.ValueRange")})
    private com.google.api.services.sheets.v4.model.ValueRange values;

    public com.google.api.services.sheets.v4.model.BatchClearValuesRequest getBatchClearValuesRequest() {
        return batchClearValuesRequest;
    }

    public void setBatchClearValuesRequest(com.google.api.services.sheets.v4.model.BatchClearValuesRequest batchClearValuesRequest) {
        this.batchClearValuesRequest = batchClearValuesRequest;
    }

    public com.google.api.services.sheets.v4.model.BatchGetValuesByDataFilterRequest getBatchGetValuesByDataFilterRequest() {
        return batchGetValuesByDataFilterRequest;
    }

    public void setBatchGetValuesByDataFilterRequest(com.google.api.services.sheets.v4.model.BatchGetValuesByDataFilterRequest batchGetValuesByDataFilterRequest) {
        this.batchGetValuesByDataFilterRequest = batchGetValuesByDataFilterRequest;
    }

    public com.google.api.services.sheets.v4.model.BatchUpdateValuesByDataFilterRequest getBatchUpdateValuesByDataFilterRequest() {
        return batchUpdateValuesByDataFilterRequest;
    }

    public void setBatchUpdateValuesByDataFilterRequest(com.google.api.services.sheets.v4.model.BatchUpdateValuesByDataFilterRequest batchUpdateValuesByDataFilterRequest) {
        this.batchUpdateValuesByDataFilterRequest = batchUpdateValuesByDataFilterRequest;
    }

    public com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest getBatchUpdateValuesRequest() {
        return batchUpdateValuesRequest;
    }

    public void setBatchUpdateValuesRequest(com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest batchUpdateValuesRequest) {
        this.batchUpdateValuesRequest = batchUpdateValuesRequest;
    }

    public com.google.api.services.sheets.v4.model.ClearValuesRequest getClearValuesRequest() {
        return clearValuesRequest;
    }

    public void setClearValuesRequest(com.google.api.services.sheets.v4.model.ClearValuesRequest clearValuesRequest) {
        this.clearValuesRequest = clearValuesRequest;
    }

    public com.google.api.services.sheets.v4.model.BatchClearValuesByDataFilterRequest getContent() {
        return content;
    }

    public void setContent(com.google.api.services.sheets.v4.model.BatchClearValuesByDataFilterRequest content) {
        this.content = content;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
    }

    public com.google.api.services.sheets.v4.model.ValueRange getValues() {
        return values;
    }

    public void setValues(com.google.api.services.sheets.v4.model.ValueRange values) {
        this.values = values;
    }
}
