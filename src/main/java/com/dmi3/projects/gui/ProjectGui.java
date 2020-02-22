package com.dmi3.projects.gui;

import com.dmi3.projects.services.api.BasicWebService;

import java.util.Map;
import java.util.Scanner;

public class ProjectGui
{
    private final Scanner scanner;
    private final BasicWebService basicWebService;
    private boolean activeGui;

    public ProjectGui(BasicWebService basicWebService)
    {
        this.basicWebService = basicWebService;
        this.activeGui = false;
        this.scanner = new Scanner(System.in);
    }

    public void showGui()
    {
        setActiveGui(true);
        int n;
        do
        {
            System.out.println("1 - Process page");
            System.out.println("0 - Exit");
            n = scanner.nextInt();
            switch (n)
            {
                case 1:
                {
                    System.out.println("Please type url of page");
                    String url = scanner.next();
                    Map<String, Integer> result = basicWebService.parseWebPage(url);
                    System.out.println("Result:");
                    result.keySet().forEach(word -> System.out.println(word + " - " + result.get(word)));
                }
                break;

                case 0:
                    setActiveGui(false);
                    break;
            }
        }
        while (isActiveGui());
    }

    private boolean isActiveGui()
    {
        return activeGui;
    }

    private void setActiveGui(boolean activeGui)
    {
        this.activeGui = activeGui;
    }
}

