import { useForm } from 'react-hook-form';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function TransactionForm({ categories, onSubmit, isDeleting, isSaving, transaction, onDelete }) {
    const { register, handleSubmit, reset, formState } = useForm({
        defaultValues: {
            date: new Date().toISOString().split('T')[0]
        }
    });
    const navigate = useNavigate()

    useEffect(() => {
        if (transaction && transaction.transactionId) {
            reset({
                category: String(transaction.categoryId),
                description: transaction.description,
                amount: transaction.amount,
                date: transaction.date.split('T')[0]
            })
        } else {
            reset({ date: new Date().toISOString().split('T')[0] });
        }
    }, [reset, transaction])

    const deleteTransaction = (e, id) => {
        e.preventDefault()
        onDelete(id)
    }

    const cancelProcess = (e) => {
        e.preventDefault()
        navigate('/user/transactions')
    }


    return (
        <form className="auth-form t-form" onSubmit={handleSubmit(onSubmit)}>

            <div className='input-box'>

                {/* input category */}
                <label>Transaction Category</label><br />
                <div className='radio'>

                    {
                        categories.filter(cat => cat.enabled).map((cat) => {
                            return (
                                <span key={cat.categoryId}>
                                    <input
                                        type='radio'
                                        id={cat.categoryName}
                                        value={cat.categoryId}
                                        {...register('category', {
                                            required: "category is required"
                                        })}
                                    /><label htmlFor={cat.categoryName}>{cat.categoryName}</label>
                                </span>
                            )
                        })
                    }

                </div>
                {formState.errors.category && <small>{formState.errors.category.message}</small>}
            </div>

            {/* input description */}
            <div className='input-box'>
                <label>Transaction description</label><br />
                <input
                    type='text'
                    {...register('description', {
                        required: "Description is required!",
                        validate: value => value.trim().length > 0 || "Description is required!",
                        maxLength: {
                            value: 50,
                            message: "Description can have atmost 50 characters!"
                        }
                    })}
                />
                {formState.errors.description && <small>{formState.errors.description.message}</small>}
            </div>

            {/* input amount */}
            <div className='input-box'>
                <label>Amount</label><br />
                <input
                    type='number'
                    min='0.01'
                    step='0.01'
                    {...register('amount', {
                        required: "Amount is required!",
                        valueAsNumber: true,
                        min: { value: 0.01, message: "Amount must be greater than zero." },
                        validate: value => Number.isFinite(value) || "Enter a valid amount."
                    })}
                />
                {formState.errors.amount && <small>{formState.errors.amount.message}</small>}
            </div>

            {/* input date */}
            <div className='input-box'>
                <label>Date</label><br />
                <input
                    type='date'
                    {...register('date', { required: "Date is required!" })}
                />
                {formState.errors.date && <small>{formState.errors.date.message}</small>}
            </div>

            <div className='t-btn input-box'>
                <input type='submit' value={isSaving ? "Saving..." : 'Save transaction'}
                    disabled={isSaving}
                    className={isSaving ? "button button-fill loading" : "button button-fill"} />
                <input type='button' className='button outline' value='Cancel' onClick={(e) => cancelProcess(e)} />

            </div>
            {
                transaction ?
                    <div className='t-btn input-box'>
                        <button
                            className={isDeleting ? "button delete loading" : "button delete"}
                            disabled={isDeleting}
                            onClick={(e) => deleteTransaction(e, transaction.transactionId)} 
                        >
                            {isDeleting ? "Deleting..." : 'Delete transaction'} 
                        </button>
                    </div>
                    : <></>
            }
        </form>
    )
}

export default TransactionForm;