/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StoryGeneratorGUI;

import storygenerator.StoryGenerator;
import storygenerator.ProbabilityTree;
import storygenerator.Scene;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author Lindsey
 */

// GUI - full screen window
public final class GUIWindow extends JFrame{
    StoryGenerator sGen = new StoryGenerator();
    JPanel btnPanel,textPanel;
    JScrollPane scrollPane;
    JButton saveBtn, generateBtn;
    JTextPane story;
    
    public GUIWindow() {
        story = new JTextPane();
        textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1,1,1,1));
        textPanel.add(story);
        btnPanel = new JPanel();
        generateBtn = new JButton("Generate");
        saveBtn = new JButton("Save");
        scrollPane = new JScrollPane(textPanel);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        
        setTitle("Story Generator");
        setMinimumSize(new Dimension(600, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        btnPanel.setLayout(new GridLayout(2, 1, 1, 1));
        btnPanel.add(generateBtn);
        btnPanel.add(saveBtn);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel mainBtnPanel = new JPanel();
        mainBtnPanel.setLayout(new GridLayout(1, 2, 1, 1));
        mainBtnPanel.add(new JPanel());
        mainBtnPanel.add(btnPanel);
        mainBtnPanel.add(new JPanel());
        add(mainBtnPanel, BorderLayout.SOUTH); 
        this.addListeners();
    }
    
    public void addListeners() {
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                sGen.generateStory(sGen.sceneMap);
                story.setText(sGen.storyText);
            }
        });
        
        //action listener for the save button
        saveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //get a file chooser, add a filter for txt only
                JFileChooser c = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("txt text files", "txt");
                c.setDialogTitle("Save As");
                c.setFileFilter(filter);

                //if user enters a correct filename
                int saveValue = c.showSaveDialog(GUIWindow.this);
                if (saveValue == JFileChooser.APPROVE_OPTION) {
                    //Test the string for .txt extention
                    String fileName = (c.getSelectedFile().toString());
                    if (fileName.contains(".txt") == false) {
                        fileName += ".txt";
                    }

                    //create document
                    writeToFile(fileName, story.getText());
                }
            }
        });   
    }
    
    public static void writeToFile(String fileName, String text) {
        try {
            Files.write(Paths.get(fileName),
                        text.getBytes(),
                        StandardOpenOption.CREATE);
        }
        catch(IOException e)
        {
            //do nothing
        }
    };
}