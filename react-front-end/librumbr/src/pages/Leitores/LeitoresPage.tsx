import './LeitoresPage.css';
import { useContext } from 'react';
import { ThemeContext } from '../../layout/ThemeContext'; 
import {
  Button,
  Table,
  Typography,
  Modal,
  Form,
  Input,
  message,
  Space,
} from 'antd';
import { useState } from 'react';

const { Title } = Typography;

const LeitoresPage = () => {
  const { darkTheme } = useContext(ThemeContext);
  const [form] = Form.useForm();
  const [modalAberto, setModalAberto] = useState(false);
  const [leitores, setLeitores] = useState<any[]>([]);
  const [messageApi, contextHolder] = message.useMessage();

  const colunas = [
    { title: 'Nome', dataIndex: 'nome', key: 'nome' },
    { title: 'E-mail', dataIndex: 'email', key: 'email' },
    { title: 'CPF', dataIndex: 'cpf', key: 'cpf' },
    { title: 'Telefone', dataIndex: 'telefone', key: 'telefone' },
  ];

  const abrirModal = () => {
    form.resetFields();
    setModalAberto(true);
  };

  const fecharModal = () => {
    setModalAberto(false);
  };

  const salvarLeitor = (valores: any) => {
    setLeitores([...leitores, { ...valores, key: leitores.length + 1 }]);
    messageApi.success('Leitor cadastrado com sucesso!');
    fecharModal();
  };

  return (
    <>
      {contextHolder}
      <div className={`leitores-container ${darkTheme ? 'dark' : 'light'}`}>
        <Space
          style={{
            display: 'flex',
            justifyContent: 'space-between',
            marginBottom: 16,
          }}
        >
          <Title level={2} className="leitores-title">
            Leitores
          </Title>
          <Button type="primary" onClick={abrirModal}>
            Novo Leitor
          </Button>
        </Space>

        <Table
          columns={colunas}
          dataSource={leitores}
          pagination={{ pageSize: 5 }}
        />

        <Modal
          open={modalAberto}
          title="Cadastrar Novo Leitor"
          onCancel={fecharModal}
          onOk={() => form.submit()}
          okText="Salvar"
        >
          <Form form={form} layout="vertical" onFinish={salvarLeitor}>
            <Form.Item
              name="nome"
              label="Nome completo"
              rules={[{ required: true }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              name="email"
              label="E-mail"
              rules={[{ required: true, type: 'email' }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              name="cpf"
              label="CPF"
              rules={[{ required: true }]}
            >
              <Input maxLength={14} />
            </Form.Item>
            <Form.Item name="telefone" label="Telefone">
              <Input maxLength={15} />
            </Form.Item>
          </Form>
        </Modal>
      </div>
    </>
  );
};

export default LeitoresPage;
