import { theme } from 'antd';
import type { ThemeConfig } from 'antd/es/config-provider/context';

const customTheme: ThemeConfig = {
  algorithm: theme.darkAlgorithm,
  token: {
    colorPrimary: '#6C63FF', 
    colorBgBase: '#1e1e2f', 
    colorTextBase: '#ffffff', 
    borderRadius: 8,
  },
};

export default customTheme;
