package com.dmi3.projects.gui;

import java.util.Scanner;

public class ProjectGui
{
    private final Scanner scanner;
    private boolean activeGui;

    public ProjectGui()
    {
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
                    String urlName = scanner.nextLine();

                }
                break;

                case 2:
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

