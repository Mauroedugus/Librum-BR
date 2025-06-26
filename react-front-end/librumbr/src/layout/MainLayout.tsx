import { Layout, Menu, Button } from 'antd';
import {
  ArrowLeftOutlined,
  ArrowRightOutlined,
  BookOutlined,
  TeamOutlined,
  HomeOutlined,
  PlusCircleOutlined,
  SunOutlined,
  MoonOutlined, 
} from '@ant-design/icons';
import { useNavigate, Outlet, useLocation } from 'react-router-dom';
import { useState } from 'react';
import logoCompleta from '../assets/logo-completa.png';
import logoCompacta from '../assets/lb.png';
import { ThemeContext } from './ThemeContext';
import './MainLayout.css';

const { Sider, Content } = Layout;

const MainLayout = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [collapsed, setCollapsed] = useState(false);
  const [darkTheme, setDarkTheme] = useState(true);

  const toggleCollapsed = () => {
    setCollapsed(!collapsed);
  };

  const toggleTheme = () => {
    setDarkTheme(!darkTheme);
  };

  const menuItems = [
    { key: '/dashboard', label: 'Dashboard', icon: <HomeOutlined /> },
    { key: '/livros/cadastrar', label: 'Cadastrar Livro', icon: <BookOutlined /> },
    { key: '/exemplares/cadastrar', label: 'Cadastrar Exemplar', icon: <PlusCircleOutlined /> },
    { key: '/leitores', label: 'Leitores', icon: <TeamOutlined /> },
  ];

  return (
    <ThemeContext.Provider value={{ darkTheme, toggleTheme }}>
      <Layout style={{ minHeight: '100vh', background: darkTheme ? '#1e1e2f' : '#fff' }}>
        <Sider
          collapsible
          collapsed={collapsed}
          trigger={null}
          width={220}
          style={{ backgroundColor: '#2d2b55' }}
        >
          <div
            style={{
              height: 100,
              display: 'flex',
              alignItems: 'center',
              justifyContent: collapsed ? 'center' : 'space-between',
              padding: collapsed ? '0' : '0 20px',
              backgroundColor: '#2d2b55',
              borderBottom: darkTheme ? '1px solid #6C63FF' : '1px solid #d9d9d9',
              position: 'relative',
            }}
          >
            <img
              src={collapsed ? logoCompacta : logoCompleta}
              alt="Logo LibrumBR"
              style={{
                height: collapsed ? 64 : 150,
                transition: 'all 0.3s ease',
                objectFit: 'contain',
              }}
            />

            <Button
              type="text"
              icon={collapsed ? <ArrowRightOutlined /> : <ArrowLeftOutlined />}
              onClick={toggleCollapsed}
              style={{
                color: darkTheme ? '#fff' : '#000',
                position: 'absolute',
                top: 12,
                right: 12,
              }}
            />
          </div>

          <Menu
            className="menu-fixo"
            mode="inline"
            selectedKeys={[location.pathname]}
            items={menuItems}
            onClick={({ key }) => navigate(key)}
            theme="dark" 
            style={{
              backgroundColor: '#2d2b55',
              color: '#fff',
              borderRight: 0,
            }}
          />
        </Sider>

        <Layout>
          <div
            style={{
              backgroundColor: darkTheme ? '#1e1e2f' : '#f0f2f5',
              padding: '10px 24px',
              textAlign: 'right',
            }}
          >
            <Button onClick={toggleTheme} icon={darkTheme ? <SunOutlined /> : <MoonOutlined />} />

          </div>

          <Content style={{
            backgroundColor: darkTheme ? '#1e1e2f' : '#f0f2f5'
          }}>
            <Outlet />
          </Content>
        </Layout>
      </Layout>
    </ThemeContext.Provider>

  );
};

export default MainLayout;
