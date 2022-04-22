package com.github.alisonli.historyplugin.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class JiraSettings {
    private JPanel rootPanel;
    private JTextField endpointURLField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JiraConfig config;

    public void createGUI(Project project) {
        this.config = JiraConfig.getInstance(project);
        endpointURLField.setText(config.getEndpointURL());
        usernameField.setText(config.getUsername());
    }

    boolean isModified() {
        boolean modified;
        modified = !endpointURLField.getText().equals(config.getEndpointURL());
        modified |= !usernameField.getText().equals(config.getUsername());
        String currentPassword = config.getPassword();
        if (currentPassword != null) {
            modified |= !Arrays.equals(passwordField.getPassword(), currentPassword.toCharArray());
        }
        return modified;
    }

    void apply() throws ConfigurationException {
        try {
            URL url = new URL(endpointURLField.getText());
            url.toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new ConfigurationException("Invalid URL");
        }
        config.setEndpointURL(endpointURLField.getText());
        config.setUsername(usernameField.getText());
        config.setPassword(String.valueOf(passwordField.getPassword()));
    }

    JPanel getRootPanel() {
        return rootPanel;
    }
}
