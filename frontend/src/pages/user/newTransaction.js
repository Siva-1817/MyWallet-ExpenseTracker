import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UserService from '../../services/userService';
import TransactionForm from '../../components/userTransactions/transactionForm';
import TransactionTypeSelectWrapper from '../../components/userTransactions/transactionTypeSelectWrapper';
import Header from '../../components/utils/header';
import Loading from '../../components/utils/loading';
import useCategories from '../../hooks/useCategories';
import Info from '../../components/utils/Info';
import Container from '../../components/utils/Container';
import toast, { Toaster } from 'react-hot-toast';

const transactionTypes = [{ 'id': 1, 'name': 'Expense' }, { 'id': 2, 'name': 'Income' }]

function NewTransaction() {

    const [categories, isFetching] = useCategories();
    const [filteredCategories, setFilteredCategories] = useState([]);
    const [activeTransactionType, setTransactionType] = useState(1);
    const [isSaving, setIsSaving] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        setFilteredCategories(categories.filter(cat => cat.transactionType.transactionTypeId === activeTransactionType));
    }, [categories, activeTransactionType])

    const onSubmit = async (data) => {
        setIsSaving(true);
        try {
            const response = await UserService.add_transaction(
                Number(data.category),
                data.description.trim(),
                Number(data.amount),
                data.date
            );
            if (response.data.status === "SUCCESS") {
                navigate("/user/transactions", { state: { text: response.data.response } });
            } else {
                toast.error(response.data.response || "Failed to add transaction.");
            }
        } catch (error) {
            const message = error.response?.data?.response || error.response?.data?.message;
            toast.error(typeof message === "string" ? message : "Failed to add transaction. Please try again.");
        } finally {
            setIsSaving(false);
        }
    }


    return (
        <Container activeNavId={2}>
            <Header title="New Transaction" />
            <Toaster/>
            {(isFetching) && <Loading />}
            {(!isFetching && categories.length === 0) && <Info text="No data found!" />}
            {
                (!isFetching && categories.length !== 0) && (
                    <>
                        <TransactionTypeSelectWrapper
                            transactionTypes={transactionTypes}
                            setTransactionType={setTransactionType}
                            activeTransactionType={activeTransactionType}
                        />
                        <TransactionForm categories={filteredCategories} onSubmit={onSubmit} isSaving={isSaving} />
                    </>
                )
            }
        </Container>
    )
}

export default NewTransaction;