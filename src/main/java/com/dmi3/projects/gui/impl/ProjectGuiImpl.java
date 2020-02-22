package com.dmi3.projects.gui.impl;

import com.dmi3.projects.exceptions.AbstractException;
import com.dmi3.projects.gui.api.ProjectGui;
import com.dmi3.projects.services.api.BasicWebService;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ProjectGuiImpl implements ProjectGui
{
    private final BasicWebService basicWebService;
    private UrlValidator urlValidator;

    public ProjectGuiImpl(BasicWebService basicWebService)
    {
        this.basicWebService = basicWebService;
        this.urlValidator = new UrlValidator();
    }

    public void run()
    {
        try (Scanner scanner = new Scanner(System.in))
        {
            showGui(scanner);
        }
    }

    private void showGui(Scanner scanner)
    {
        Integer intOption;
        do
        {
            System.out.println("1 - Process page");
            System.out.println("0 - Exit");

            intOption = null;
            while (intOption == null)
            {
                intOption = askInt(scanner);
            }
            switch (intOption)
            {
                case 1:
                {
                    System.out.println("Please type url of page");
                    String url = scanner.next();
                    while (!urlValidator.isValid(url))
                    {
                        scanner.nextLine();
                        url = scanner.next();
                    }
                    try
                    {
                        Map<String, Integer> result = basicWebService.parseWebPage(url);
                        System.out.println("Result:");
                        result.keySet().forEach(word -> System.out.println(word + " - " + result.get(word)));

                        System.out.println("Save result to file? (true/false):");
                        Boolean booleanOption;
                        booleanOption = null;
                        while (booleanOption == null)
                        {
                            booleanOption = askBoolean(scanner);
                        }
                        if (booleanOption)
                        {
                            basicWebService.saveResultToFile(result);
                        }
                    }
                    catch (AbstractException ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                }
                break;

                case 0:
                default:
                    break;
            }
        }
        while (intOption != 0);
    }

    private Integer askInt(Scanner scanner)
    {
        try
        {
            return scanner.nextInt();
        }
        catch (InputMismatchException ex)
        {
            System.out.println("Please write number value");
            scanner.nextLine();
        }

        return null;
    }

    private Boolean askBoolean(Scanner scanner)
    {
        try
        {
            return scanner.nextBoolean();
        }
        catch (InputMismatchException ex)
        {
            System.out.println("Please write true/false value");
            scanner.nextLine();
        }

        return null;
    }
}

