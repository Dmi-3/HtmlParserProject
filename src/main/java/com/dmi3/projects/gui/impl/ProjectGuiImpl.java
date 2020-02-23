package com.dmi3.projects.gui.impl;

import com.dmi3.projects.gui.api.ProjectGui;
import com.dmi3.projects.services.api.BasicWebService;
import com.dmi3.projects.services.impl.BasicWebServicesImpl;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ProjectGuiImpl implements ProjectGui
{
    private final BasicWebService basicWebService;
    private UrlValidator urlValidator;

    public ProjectGuiImpl()
    {
        this.basicWebService = new BasicWebServicesImpl();
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
            System.out.println("2 - Process file");
            System.out.println("0 - Exit");

            intOption = null;
            while (intOption == null)
            {
                intOption = askInt(scanner);
                scanner.nextLine();
            }
            switch (intOption)
            {
                case 1:
                {
                    processPage(scanner);
                }
                break;

                case 2:
                {
                    processFile(scanner);
                }
                break;

                case 0:
                default:
                    break;
            }
        }
        while (intOption != 0);
    }

    private void processPage(Scanner scanner)
    {
        System.out.println("Please type url of page");
        String url = scanner.nextLine();
        while (!urlValidator.isValid(url))
        {
            System.out.println("Please write valid url (example: https://www.simbirsoft.com)");
            url = scanner.next();
        }
        try
        {
            Map<String, Integer> result = basicWebService.parseWebPage(url);
            processResult(result, scanner);
        }
        catch (RuntimeException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void processFile(Scanner scanner)
    {
        System.out.println("Please type path to file");
        String filePath = scanner.nextLine();
        File file = new File(filePath);
        if (!file.exists())
        {
            System.out.println("File was not found.");
            return;
        }

        Map<String, Integer> result = basicWebService.parseFile(filePath);
        processResult(result, scanner);
    }

    private void processResult(Map<String, Integer> result, Scanner scanner)
    {
        System.out.println("Show result? (true/false):");
        Boolean booleanOption = null;
        while (booleanOption == null)
        {
            booleanOption = askBoolean(scanner);
            scanner.nextLine();
        }
        if (booleanOption)
        {
            System.out.println("Result:");
            result.keySet().forEach(word -> System.out.println(word + " - " + result.get(word)));
        }
        System.out.println("Save result to file? (true/false):");
        booleanOption = null;
        while (booleanOption == null)
        {
            booleanOption = askBoolean(scanner);
        }
        if (booleanOption)
        {
            basicWebService.saveResultToFile(result);
            System.out.println("Result was successfully saved.");
        }
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
        }

        return null;
    }
}

