package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ast.NodeProgram;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

public class CompilatoreACDC {
	
	private static String filePath;
	private static String salvaFile;

	public static void main(String[] args) {
		Locale.setDefault(Locale.ITALIAN);
		JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "File di testo", "txt");
        
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Selezionare file da compilare");
        int returnVal = chooser.showOpenDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	filePath = chooser.getSelectedFile().getAbsolutePath();
        }
        else
        	return;
        
        try {
			generazioneCodice();
		} catch (FileNotFoundException | SyntacticException e) {
			JDialog frame = new JDialog();
			JOptionPane.showMessageDialog(frame, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}  
               
	}

	private static void generazioneCodice() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner(filePath)).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		if(tcVisit.getLog() != "") {
			JDialog frame = new JDialog();
			JOptionPane.showMessageDialog(frame, tcVisit.getLog(), "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		if(cgVisit.getLog() != "") {
			JDialog frame = new JDialog();
			JOptionPane.showMessageDialog(frame, cgVisit.getLog(), "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			JDialog frame = new JDialog();
			JOptionPane.showMessageDialog(frame, "Compilazione avvenuta con successo.", "Successo", JOptionPane.PLAIN_MESSAGE);
			scritturaFile(cgVisit.getCodiceGenerato());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			JDialog frame = new JDialog();
			JOptionPane.showMessageDialog(frame, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void scritturaFile(String codice) throws FileNotFoundException, UnsupportedEncodingException {
		JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "File di testo", "txt");
        
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Salva file");
        chooser.setApproveButtonText("Salva");
        chooser.setApproveButtonToolTipText("Salva file");
        int returnVal = chooser.showOpenDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	salvaFile = chooser.getSelectedFile().getName();
        }
        else
        	return;
		PrintWriter writer = new PrintWriter(salvaFile + ".txt", "UTF-8");
		writer.println(codice);
		writer.close();
	}

}
