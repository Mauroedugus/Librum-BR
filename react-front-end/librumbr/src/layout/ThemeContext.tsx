import { createContext, useContext } from 'react';

export const ThemeContext = createContext({
  darkTheme: true,
  toggleTheme: () => {},
});

export const useTheme = () => useContext(ThemeContext);