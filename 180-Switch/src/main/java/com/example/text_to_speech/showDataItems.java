

//NewGen Coders - Piyush Rana, Tunde Olokun, Kachail Fahmid

package com.example.text_to_speech;

/**
 * Created by piyus on 2017-12-09.
 */


//This class is used to retrieve the data from the firebase:
public class showDataItems {

    private String File_URL,File_Title;

    public showDataItems(String file_URL,String file_Title)
    {
        File_URL =  file_URL;
        File_Title = file_Title;

    }
    public showDataItems()
    {
        // We require en empty Constructor
    }

    public String getFile_Title() {
        return File_Title;
    }

    public void setFile_Title(String file_Title) {
        File_Title = file_Title;
    }

    public String getFile_URL() {
        return File_URL;
    }

    public void setFile_URL(String file_URL) {
        File_URL = file_URL;
    }
}
