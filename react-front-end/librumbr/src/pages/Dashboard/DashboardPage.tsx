import { Card, Table, Typography } from 'antd';
import './DashboardPage.css';
import { useTheme } from '../../layout/ThemeContext';

const { Title } = Typography;

// Mocks
const estatisticas = [
  { titulo: 'Livros Disponíveis', valor: 125 },
  { titulo: 'Livros Emprestados', valor: 38 },
  { titulo: 'Leitores Ativos', valor: 52 },
];

const dadosVencendo = [
  {
    key: '1',
    livro: 'Dom Casmurro',
    leitor: 'Ana Souza',
    vencimento: '25/06/2025',
  },
  {
    key: '2',
    livro: 'A Revolução dos Bichos',
    leitor: 'Carlos Lima',
    vencimento: '26/06/2025',
  },
];

const colunas = [
  { title: 'Livro', dataIndex: 'livro', key: 'livro' },
  { title: 'Leitor', dataIndex: 'leitor', key: 'leitor' },
  { title: 'Vencimento', dataIndex: 'vencimento', key: 'vencimento' },
];

const DashboardPage = () => {
  const { darkTheme } = useTheme();

  const bgColor = darkTheme ? '#2d2b55' : '#fff';
  const textColor = darkTheme ? '#fff' : '#000';
  const destaqueColor = darkTheme ? '#6C63FF' : '#4B4AEF';

  return (
    <div style={{ padding: '24px' }}>
      <Title level={2} style={{ color: textColor }}>Dashboard</Title>

      <div
        style={{
          display: 'flex',
          flexWrap: 'wrap',
          gap: 16,
          marginBottom: 24,
        }}
      >
        {estatisticas.map((estat, index) => (
          <Card
            key={index}
            style={{
              backgroundColor: bgColor,
              color: textColor,
              borderColor: destaqueColor,
              width: '250px',
              flex: '1 1 250px',
            }}
            styles={{
              body: { color: textColor }
            }}
          >
            <Title level={4} style={{ color: textColor }}>{estat.titulo}</Title>
            <Title level={2} style={{ color: destaqueColor, margin: 0 }}>{estat.valor}</Title>
          </Card>
        ))}
      </div>

      <Card
        title={<span style={{ color: textColor }}>Empréstimos Próximos ao Vencimento</span>}
        style={{
          backgroundColor: bgColor,
          borderColor: destaqueColor,
          overflowX: 'auto',
        }}
        styles={{
          header: { borderBottom: `1px solid ${destaqueColor}` },
          body: { padding: 0 }
        }}
      >
        <div style={{ overflowX: 'auto' }}>
          <Table
            columns={colunas}
            dataSource={dadosVencendo}
            pagination={false}
            style={{ minWidth: 500 }}
            rowClassName={() => darkTheme ? 'tabela-dark' : 'tabela-light'}
          />
        </div>
      </Card>
    </div>
  );
};

export default DashboardPage;
