package com.czn.shoppingmall.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IFtpService {

    public boolean upload(List<File> fileList) throws IOException;
}
